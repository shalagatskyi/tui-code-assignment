package tui.meta.challenge.quotes.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tui.meta.challenge.quotes.controller.model.QuoteDto;
import tui.meta.challenge.quotes.service.QuoteService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tui.meta.challenge.quotes.factory.QuoteFactory.getQuoteEntity;

@ExtendWith(MockitoExtension.class)
class QuoteControllerTest {

    @Mock
    private QuoteService quoteService;

    @InjectMocks
    private QuoteController quoteController;

    @Test
    void shouldReturnQuoteDtoById_WhenQuoteExists() {
        var entity = getQuoteEntity("1");
        var dto = new QuoteDto(entity);

        when(quoteService.getSingleQuote("1")).thenReturn(dto);

        QuoteDto result = quoteController.getQuote("1");

        assertEquals(dto, result);
        verify(quoteService).getSingleQuote("1");
    }

    @Test
    void shouldReturnFilteredQuotes_WhenAuthorIsProvided() {
        var entity = getQuoteEntity("2", "Steve Jobs");
        var dto = new QuoteDto(entity);

        when(quoteService.getQuotesByAuthor("Steve Jobs")).thenReturn(List.of(dto));

        List<QuoteDto> result = quoteController.getQuotesWithSearch("Steve Jobs");

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(quoteService).getQuotesByAuthor("Steve Jobs");
    }

    @Test
    void shouldReturnAllQuotes_WhenAuthorIsNotProvided() {
        var dto1 = new QuoteDto(getQuoteEntity("1"));
        var dto2 = new QuoteDto(getQuoteEntity("2"));

        when(quoteService.getAllQuotes()).thenReturn(List.of(dto1, dto2));

        List<QuoteDto> result = quoteController.getQuotesWithSearch(null);

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
        verify(quoteService).getAllQuotes();
    }
}
