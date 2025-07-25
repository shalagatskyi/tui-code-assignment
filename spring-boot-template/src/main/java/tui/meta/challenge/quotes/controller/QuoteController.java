package tui.meta.challenge.quotes.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tui.meta.challenge.quotes.controller.model.QuoteDto;
import tui.meta.challenge.quotes.service.QuoteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
@Validated
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping(path = "/{id}", produces = "application/json")
    public QuoteDto getQuote(@PathVariable(value = "id") String id) {
        return quoteService.getSingleQuote(id);
    }

    @GetMapping(produces = "application/json")
    public List<QuoteDto> getQuotesWithSearch(
            @RequestParam(value = "author", required = false)
            @Valid
            @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters.")
            String author) {
        if (author != null) {
            return quoteService.getQuotesByAuthor(author);
        }

        return quoteService.getAllQuotes();
    }
}
