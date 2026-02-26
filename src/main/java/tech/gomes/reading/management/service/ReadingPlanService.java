package tech.gomes.reading.management.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.ReadingPlanBuilder;
import tech.gomes.reading.management.builder.ReadingPlanResponseDTOBuilder;
import tech.gomes.reading.management.controller.filter.ReadingPlanFilter;
import tech.gomes.reading.management.domain.JoinPlanTemplate;
import tech.gomes.reading.management.domain.PlanCategory;
import tech.gomes.reading.management.domain.ReadingPlan;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
import tech.gomes.reading.management.dto.readingPlan.request.BookTemplatePlanDTO;
import tech.gomes.reading.management.dto.readingPlan.request.PlanRequestDTO;
import tech.gomes.reading.management.dto.readingPlan.request.PrivacyPlanDTO;
import tech.gomes.reading.management.dto.readingPlan.response.PlanResponseDTO;
import tech.gomes.reading.management.exception.ReadingPlanException;
import tech.gomes.reading.management.repository.BookTemplateRepository;
import tech.gomes.reading.management.repository.PlanCategoryRepository;
import tech.gomes.reading.management.repository.ReadingPlanRepository;
import tech.gomes.reading.management.repository.specification.ReadingPlanSpecification;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReadingPlanService {

    private final ReadingPlanRepository repository;

    private final BookTemplateRepository bookTemplateRepository;

    private final PlanCategoryRepository planCategoryRepository;

    public ReadingPlanPageDTO findAllPlans(User user, ReadingPlanFilter filter) {

        Specification<ReadingPlan> spec = ReadingPlanSpecification.byFilter(user, filter);

        Page<ReadingPlan> plans = repository.findAll(spec, getPageable(filter));

        return ReadingPlanResponseDTOBuilder.fromPageReadingPlan(plans);
    }

    @Transactional
    public PlanResponseDTO createReadingPlan(PlanRequestDTO requestDTO, User user) throws Exception {
        if (repository.existsByTitleAndUserId(requestDTO.title(), user.getId())) {
            throw new ReadingPlanException("Já existe um plano com esse título.", HttpStatus.BAD_REQUEST);
        }

        Set<JoinPlanTemplate> joinPlans = createJoinPlanTemplates(requestDTO);

        Set<PlanCategory> categories = planCategoryRepository.findAllByIdIn(requestDTO.categories());

        if (categories.isEmpty()) {
            throw new ReadingPlanException("Nenhuma categoria foi encontrada.", HttpStatus.NOT_FOUND);
        }

        ReadingPlan newPlan = ReadingPlanBuilder.fromRequestDTO(requestDTO, user, joinPlans, categories);

        ReadingPlan readingPlan = repository.save(newPlan);

        return ReadingPlanResponseDTOBuilder.fromReadingPlan(readingPlan);
    }

    @Transactional
    public PlanResponseDTO updateReadingPlan(long id, PlanRequestDTO requestDTO, User user) throws Exception {

        ReadingPlan readingPlan = findByIdForUser(id, user.getId());

        if (!(requestDTO.title().equalsIgnoreCase(readingPlan.getTitle())) && repository.existsByTitleAndUserId(requestDTO.title(), user.getId())) {
            throw new ReadingPlanException("Já existe um plano com esse título.", HttpStatus.BAD_REQUEST);
        }

        Set<JoinPlanTemplate> joinPlans = createJoinPlanTemplates(requestDTO);

        Set<PlanCategory> categories = planCategoryRepository.findAllByIdIn(requestDTO.categories());

        if (categories.isEmpty()) {
            throw new ReadingPlanException("Nenhuma categoria foi encontrada.", HttpStatus.NOT_FOUND);
        }

        ReadingPlanBuilder.updateFromReadingPlan(requestDTO, readingPlan, joinPlans, categories);

        ReadingPlan updatedPlan = repository.save(readingPlan);

        return ReadingPlanResponseDTOBuilder.fromReadingPlan(updatedPlan);
    }

    @Transactional
    public PlanResponseDTO updatePrivacyFromPlan(PrivacyPlanDTO requestDTO, User user) throws ReadingPlanException {
        ReadingPlan plan = findByIdForUser(requestDTO.id(), user.getId());

        if (requestDTO.privacyStatus() != plan.getIsPublic()) {
            plan.setIsPublic(requestDTO.privacyStatus());
            plan = repository.save(plan);
        }

        return ReadingPlanResponseDTOBuilder.fromReadingPlan(plan);
    }

    public void deletePlan(long id, User user) throws ReadingPlanException {
        ReadingPlan plan = findByIdForUser(id, user.getId());

        repository.delete(plan);
    }

    private ReadingPlan findByIdForUser(long id, long userId) throws ReadingPlanException {
        return repository.findByIdAndUserId(id, userId).orElseThrow(() ->
                new ReadingPlanException("Nenhuma plano foi encontrado", HttpStatus.NOT_FOUND));
    }

    private Set<JoinPlanTemplate> createJoinPlanTemplates(PlanRequestDTO requestDTO) throws ReadingPlanException {

        Set<JoinPlanTemplate> joinPlans = new HashSet<>();

        validatePositionFromTemplates(requestDTO.booksTemplates());

        for (BookTemplatePlanDTO planDTO : requestDTO.booksTemplates()) {
            if (bookTemplateRepository.existsById(planDTO.templateId())) {
                JoinPlanTemplate jp = JoinPlanTemplate.builder()
                        .order(planDTO.position())
                        .templateId(planDTO.templateId())
                        .description(planDTO.description())
                        .build();

                joinPlans.add(jp);
            }
        }
        return joinPlans;
    }

    private void validatePositionFromTemplates(Set<BookTemplatePlanDTO> requestDTOS) throws ReadingPlanException {
        Set<Integer> positions = new HashSet<>();
        Set<Integer> duplicatedPositions = new HashSet<>();

        for (BookTemplatePlanDTO bookPosition : requestDTOS) {
            if (!positions.add(bookPosition.position())) {
                duplicatedPositions.add(bookPosition.position());
            }
        }

        if (!duplicatedPositions.isEmpty()) {
            throw new ReadingPlanException("Alguns livros estão em posições repetidas: " + duplicatedPositions, HttpStatus.BAD_REQUEST);
        }
    }

    private Pageable getPageable(ReadingPlanFilter filter) {
        Sort sort = Sort.by(Sort.Direction.valueOf(filter.getDirection()), filter.getType().getValue());

        return PageRequest.of(filter.getPage(), filter.getPageSize(), sort);
    }
}