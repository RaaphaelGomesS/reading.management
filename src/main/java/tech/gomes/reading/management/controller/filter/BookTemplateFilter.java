package tech.gomes.reading.management.controller.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookTemplateFilter(String author, String ISBN, String title, int page, int pageSize) {
}
