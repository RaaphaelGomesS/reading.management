package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.SuggestionResponseDTOBuilder;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.suggestion.request.DeclineRequestDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponseDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdateResponseDTO;
import tech.gomes.reading.management.exception.SuggestionException;
import tech.gomes.reading.management.indicator.SuggestionStatusIndicator;
import tech.gomes.reading.management.repository.SuggestionRepository;

@Service
@RequiredArgsConstructor
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;

    private final BookTemplateService bookTemplateService;

    public SuggestionResponsePageDTO findAllCreationSuggestion(int page, int pageSize, String direction, String status) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "created_at");

        Page<SuggestionTemplate> suggestionPage = suggestionRepository.findByStatusAndBookTemplateIsNull(status, pageable);

        return SuggestionResponseDTOBuilder.fromPage(suggestionPage);
    }

    public SuggestionResponsePageDTO findAllUpdateSuggestion(int page, int pageSize, String direction, String status) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "created_at");

        Page<SuggestionTemplate> suggestionPage = suggestionRepository.findByStatusAndBookTemplateIsNotNull(status, pageable);

        return SuggestionResponseDTOBuilder.fromPage(suggestionPage);
    }

    public SuggestionUpdateResponseDTO findUpdateSuggestionWithTemplate(long suggestionId) throws Exception {

        SuggestionTemplate suggestion = suggestionRepository.findByIdAndBookTemplateIsNotNull(suggestionId)
                .orElseThrow(() -> new SuggestionException("Não foi encontrado nenhuma sugestão com esse id", HttpStatus.NOT_FOUND));

        return SuggestionResponseDTOBuilder.toUpdate(suggestion, suggestion.getBookTemplate());
    }

    public SuggestionResponseDTO findCreateSuggestionById(long suggestionId) throws Exception {

        SuggestionTemplate suggestion = suggestionRepository.findByIdAndBookTemplateIsNull(suggestionId)
                .orElseThrow(() -> new SuggestionException("Não foi encontrado nenhuma sugestão com esse id", HttpStatus.NOT_FOUND));

        return SuggestionResponseDTOBuilder.fromSuggestion(suggestion);
    }

    public void approveSuggestion(long id) throws Exception {

        SuggestionTemplate suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionException("Não foi encontrado nenhuma sugestão com esse id", HttpStatus.NOT_FOUND));

        if (suggestion.getBookTemplate() != null) {
            bookTemplateService.updateBookTemplateBySuggestion(suggestion);
        } else {
            bookTemplateService.createBookTemplateBySuggestion(suggestion);
        }

        suggestion.setStatus(SuggestionStatusIndicator.APPROVE);

        suggestionRepository.save(suggestion);
    }

    public void declineSuggestion(DeclineRequestDTO requestDTO) throws Exception {
        SuggestionTemplate suggestion = suggestionRepository.findById(requestDTO.id())
                .orElseThrow(() -> new SuggestionException("Não foi encontrado nenhuma sugestão com esse id", HttpStatus.NOT_FOUND));

        suggestion.setJustification(requestDTO.justification());
        suggestion.setStatus(SuggestionStatusIndicator.DECLINE);

        suggestionRepository.save(suggestion);
    }
}
