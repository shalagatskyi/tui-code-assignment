package tui.meta.challenge.quotes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tui.meta.challenge.quotes.controller.exceptions.QuoteNotFoundException;
import tui.meta.challenge.quotes.controller.model.QuoteDto;
import tui.meta.challenge.quotes.mapping.QuoteEntityToDtoFunc;
import tui.meta.challenge.quotes.repository.QuoteRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tui.meta.challenge.quotes.factory.QuoteFactory.getQuoteEntity;

@ExtendWith(MockitoExtension.class)
class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepository;
    @Mock
    private QuoteEntityToDtoFunc quoteEntityToDtoFunc;
    @InjectMocks
    private QuoteServiceImpl quoteService;

    @Test
    void shouldReturnQuoteDto_WhenQuoteExists() {
        var entity = getQuoteEntity("1");
        var dto = new QuoteDto(entity);

        when(quoteRepository.findById("1")).thenReturn(Optional.of(entity));
        when(quoteEntityToDtoFunc.apply(entity)).thenReturn(dto);

        QuoteDto result = quoteService.getSingleQuote("1");

        assertEquals(dto, result);
        verify(quoteRepository).findById("1");
        verify(quoteEntityToDtoFunc).apply(entity);
    }

    @Test
    void shouldThrowException_WhenQuoteNotFound() {
        when(quoteRepository.findById("404")).thenReturn(Optional.empty());

        QuoteNotFoundException exception = assertThrows(QuoteNotFoundException.class,
                () -> quoteService.getSingleQuote("404"));

        assertTrue(exception.getMessage().contains("quote with given id:404 is missing"));
    }

    @Test
    void shouldReturnQuotesBySanitizedAuthor() {
        var rawAuthor = "St@eve Jo$bs!";
        var sanitized = "Steve Jobs";
        var entity = getQuoteEntity("10", sanitized);
        var dto = new QuoteDto(entity);

        when(quoteRepository.getQuoteByAuthorFullTextSearch(sanitized)).thenReturn(List.of(entity));
        when(quoteEntityToDtoFunc.apply(entity)).thenReturn(dto);

        List<QuoteDto> result = quoteService.getQuotesByAuthor(rawAuthor);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(quoteRepository).getQuoteByAuthorFullTextSearch(captor.capture());
        assertEquals(sanitized, captor.getValue());
    }

    @Test
    void shouldReturnAllQuotes() {
        var entity1 = getQuoteEntity("1");
        var entity2 = getQuoteEntity("2");
        var dto1 = new QuoteDto(entity1);
        var dto2 = new QuoteDto(entity2);

        when(quoteRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(quoteEntityToDtoFunc.apply(entity1)).thenReturn(dto1);
        when(quoteEntityToDtoFunc.apply(entity2)).thenReturn(dto2);

        List<QuoteDto> result = quoteService.getAllQuotes();

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
        verify(quoteRepository).findAll();
    }
}
