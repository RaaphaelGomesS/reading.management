package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.repository.BookRepository;
import tech.gomes.reading.management.repository.BookTemplateRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final BookTemplateRepository bookTemplateRepository;
}
