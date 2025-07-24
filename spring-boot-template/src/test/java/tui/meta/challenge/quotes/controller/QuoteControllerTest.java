package tui.meta.challenge.quotes.controller;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tui.meta.challenge.quotes.factory.QuoteFactory.getQuoteEntity;

@ExtendWith(MockitoExtension.class)
class QuoteControllerTest {

    @Mock
    private QuoteRepository quoteRepository;
    @Mock
    private QuoteEntityToDtoFunc quoteEntityToDtoFunc;
    @InjectMocks
    private QuoteController quoteController;

    @Test
    void shouldReturnQuoteDtoById_WhenQuoteExists() {
        var entity = getQuoteEntity();
        var dto = new QuoteDto(entity);

        var id = "test-id-123";
        when(quoteRepository.findById(id)).thenReturn(Optional.of(entity));
        when(quoteEntityToDtoFunc.apply(entity)).thenReturn(dto);

        QuoteDto result = quoteController.getQuote(id);

        assertEquals(dto, result);
        verify(quoteRepository).findById(id);
        verify(quoteEntityToDtoFunc).apply(entity);
    }

    @Test
    void shouldThrowException_WhenQuoteNotFoundById() {
        when(quoteRepository.findById("999")).thenReturn(Optional.empty());

        QuoteNotFoundException exception = assertThrows(QuoteNotFoundException.class, () -> {
            quoteController.getQuote("999");
        });

        assertTrue(exception.getMessage().contains("quote with given id:999 is missing"));
    }

    @Test
    void shouldReturnFilteredQuotes_WhenAuthorIsProvided() {
        var entity = getQuoteEntity();
        var dto = new QuoteDto(entity);

        var author = "Steve Jobs";
        when(quoteRepository.getQuoteByAuthorFullTextSearch(author))
                .thenReturn(List.of(entity));
        when(quoteEntityToDtoFunc.apply(entity)).thenReturn(dto);

        List<QuoteDto> result = quoteController.getQuotesWithSearch(author);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        assertEquals(author, result.get(0).author());
        verify(quoteRepository).getQuoteByAuthorFullTextSearch(author);
    }

    @Test
    void shouldReturnAllQuotes_WhenAuthorIsNotProvided() {
        var entity1 = getQuoteEntity("1");
        var entity2 = getQuoteEntity("2");

        var dto1 = new QuoteDto(entity1);
        var dto2 = new QuoteDto(entity2);

        when(quoteRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(quoteEntityToDtoFunc.apply(entity1)).thenReturn(dto1);
        when(quoteEntityToDtoFunc.apply(entity2)).thenReturn(dto2);

        List<QuoteDto> result = quoteController.getQuotesWithSearch(null);

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
        verify(quoteRepository).findAll();
    }

    @Test
    void shouldUseSanitizedAuthor_WhenAuthorContainsInvalidCharacters() {
        var author = "Steve Jobs";
        var entity = getQuoteEntity("4415", author);
        var dto = new QuoteDto(entity);

        var rawAuthor = "St@eve Jo$bs!";

        when(quoteRepository.getQuoteByAuthorFullTextSearch(author))
                .thenReturn(List.of(entity));
        when(quoteEntityToDtoFunc.apply(entity)).thenReturn(dto);

        List<QuoteDto> result = quoteController.getQuotesWithSearch(rawAuthor);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(quoteRepository).getQuoteByAuthorFullTextSearch(captor.capture());

        assertEquals(author, captor.getValue());
    }
}
