package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.repository.SuggestionRepository;

@Service
@RequiredArgsConstructor
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;

    public SuggestionResponsePageDTO findAllCreateSuggestion(int page, int pageSize, String direction, String status) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "created_at");

        return null;
    }

    public SuggestionResponsePageDTO findAllUpdateSuggestion(int page, int pageSize, String direction, String status) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "created_at");

        return null;
    }
}
