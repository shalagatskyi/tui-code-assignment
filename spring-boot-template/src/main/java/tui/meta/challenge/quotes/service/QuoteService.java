package tui.meta.challenge.quotes.service;

import tui.meta.challenge.quotes.controller.model.QuoteDto;

import java.util.List;

public interface QuoteService {
    QuoteDto getSingleQuote(String id);

    List<QuoteDto> getQuotesByAuthor(String author);

    List<QuoteDto> getAllQuotes();
}
