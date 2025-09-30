package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.SuggestionResponseDTOBuilder;
import tech.gomes.reading.management.builder.SuggestionBuilder;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.suggestion.request.DeclineRequestDTO;
import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdateResponseDTO;
import tech.gomes.reading.management.exception.SuggestionException;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;
import tech.gomes.reading.management.repository.SuggestionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;

    private final BookTemplateService bookTemplateService;

    public void createUpdateSuggestion(SuggestionRequestDTO requestDTO, User user) throws Exception {

        BookTemplate bookTemplate = bookTemplateService.findTemplateById(requestDTO.templateId());

        Optional<SuggestionTemplate> existentSuggestion = suggestionRepository.findBySuggestedISBNAndStatus(requestDTO.suggestedISBN(), TemplateStatusIndicator.IN_ANALYZE);

        if (existentSuggestion.isPresent()) {
            throw new SuggestionException("Já existe sugestão de alteração para esse template em análise", HttpStatus.BAD_REQUEST);
        }

        SuggestionTemplate suggestion = SuggestionBuilder.from(requestDTO, user, bookTemplate);

        suggestionRepository.save(suggestion);
    }

    public SuggestionResponsePageDTO findAllUpdateSuggestion(int page, int pageSize, String direction, String status) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "created_at");

        Page<SuggestionTemplate> suggestionPage = suggestionRepository.findByStatusAndBookTemplateIsNotNull(status, pageable);

        return SuggestionResponseDTOBuilder.fromPage(suggestionPage);
    }

    public SuggestionUpdateResponseDTO findUpdateSuggestion(long suggestionId) throws Exception {

        SuggestionTemplate suggestion = suggestionRepository.findByIdAndBookTemplateIsNotNull(suggestionId)
                .orElseThrow(() -> new SuggestionException("Não foi encontrado nenhuma sugestão com esse id", HttpStatus.NOT_FOUND));

        return SuggestionResponseDTOBuilder.toUpdate(suggestion, suggestion.getBookTemplate());
    }

    public void approveSuggestion(long id) throws Exception {

        SuggestionTemplate suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionException("Não foi encontrado nenhuma sugestão com esse id", HttpStatus.NOT_FOUND));

        if (suggestion.getBookTemplate() == null) {
            throw new SuggestionException("A sugestão de atualização precisa estar vinculada a um template", HttpStatus.BAD_REQUEST);
        }

        bookTemplateService.updateBookTemplateBySuggestion(suggestion);

        suggestion.setStatus(TemplateStatusIndicator.VERIFIED);

        suggestionRepository.save(suggestion);
    }

    public void declineSuggestion(DeclineRequestDTO requestDTO) throws Exception {
        SuggestionTemplate suggestion = suggestionRepository.findById(requestDTO.id())
                .orElseThrow(() -> new SuggestionException("Não foi encontrado nenhuma sugestão com esse id", HttpStatus.NOT_FOUND));

        suggestion.setJustification(requestDTO.justification());
        suggestion.setStatus(TemplateStatusIndicator.DECLINE);

        suggestionRepository.save(suggestion);
    }
}
