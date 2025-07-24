package tui.meta.challenge.quotes.mapping;

import org.junit.jupiter.api.Test;
import tui.meta.challenge.quotes.controller.model.QuoteDto;
import tui.meta.challenge.quotes.repository.model.QuoteEntity;

import static org.junit.jupiter.api.Assertions.*;
import static tui.meta.challenge.quotes.factory.QuoteFactory.getQuoteEntity;


class QuoteEntityToDtoFuncTest {

    private QuoteEntityToDtoFunc quoteEntityToDtoFunc = new QuoteEntityToDtoFunc();;

    @Test
    void shouldCorrectlyMapEntityToDto() {
        QuoteEntity quoteEntity = getQuoteEntity();

        QuoteDto resultDto = quoteEntityToDtoFunc.apply(quoteEntity);

        assertNotNull(resultDto);
        assertEquals(quoteEntity.getId(), resultDto.id());
        assertEquals(quoteEntity.getText(), resultDto.text());
        assertEquals(quoteEntity.getAuthor(), resultDto.author());
        assertEquals(quoteEntity.getGenre(), resultDto.genre());
    }

    @Test
    void shouldHandleNullAndEmptyStringFields() {
        QuoteEntity quoteEntity = getQuoteEntity("test-id-456");
        quoteEntity.setText("");
        quoteEntity.setAuthor(null);

        QuoteDto resultDto = quoteEntityToDtoFunc.apply(quoteEntity);

        assertNotNull(resultDto);
        assertEquals("", resultDto.text());
        assertNull(resultDto.author());
    }
}
