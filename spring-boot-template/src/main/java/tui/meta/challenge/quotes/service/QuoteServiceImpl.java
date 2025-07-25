package tui.meta.challenge.quotes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import tui.meta.challenge.quotes.controller.exceptions.QuoteNotFoundException;
import tui.meta.challenge.quotes.controller.model.QuoteDto;
import tui.meta.challenge.quotes.mapping.QuoteEntityToDtoFunc;
import tui.meta.challenge.quotes.repository.QuoteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final QuoteEntityToDtoFunc quoteEntityToDtoFunc;

    @Override
    @Retryable(
            retryFor = { TransientDataAccessException.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 100)
    )
    public QuoteDto getSingleQuote(String id) {
        return quoteRepository.findById(id)
                .map(quoteEntityToDtoFunc)
                .orElseThrow(() -> new QuoteNotFoundException("quote with given id:" + id + " is missing"));
    }

    @Override
    @Retryable(
            retryFor = { TransientDataAccessException.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 100)
    )
    public List<QuoteDto> getQuotesByAuthor(String author) {
        String sanitizedAuthor = author.replaceAll("[^a-zA-Z0-9\\s]", "");
        return quoteRepository.getQuoteByAuthorFullTextSearch(sanitizedAuthor).stream().map(quoteEntityToDtoFunc).toList();
    }

    @Override
    @Retryable(
            retryFor = { TransientDataAccessException.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 100)
    )
    public List<QuoteDto> getAllQuotes() {
        return quoteRepository.findAll().stream().map(quoteEntityToDtoFunc).toList();
    }
}
