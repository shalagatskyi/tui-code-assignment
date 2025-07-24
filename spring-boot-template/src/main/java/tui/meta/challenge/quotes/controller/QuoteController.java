package tui.meta.challenge.quotes.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tui.meta.challenge.quotes.controller.exceptions.QuoteNotFoundException;
import tui.meta.challenge.quotes.controller.model.QuoteDto;
import tui.meta.challenge.quotes.mapping.QuoteEntityToDtoFunc;
import tui.meta.challenge.quotes.repository.QuoteRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
@Validated
public class QuoteController {

    private final QuoteRepository quoteRepository;
    private final QuoteEntityToDtoFunc quoteEntityToDtoFunc;

    @GetMapping(path = "/{id}", produces = "application/json")
    public QuoteDto getQuote(@PathVariable(value = "id") String id) {
        return quoteRepository.findById(id)
                .map(quoteEntityToDtoFunc)
                .orElseThrow(() -> new QuoteNotFoundException("quote with given id:" + id + " is missing"));
    }

    @GetMapping(produces = "application/json")
    public List<QuoteDto> getQuotesWithSearch(
            @RequestParam(value = "author", required = false)
            @Valid
            @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters.")
            String author) {
        if (author != null) {
            String sanitizedAuthor = author.replaceAll("[^a-zA-Z0-9\\s]", "");
            return quoteRepository.getQuoteByAuthorFullTextSearch(sanitizedAuthor).stream().map(quoteEntityToDtoFunc).toList();
        }

        return quoteRepository.findAll().stream().map(quoteEntityToDtoFunc).toList();
    }
}
