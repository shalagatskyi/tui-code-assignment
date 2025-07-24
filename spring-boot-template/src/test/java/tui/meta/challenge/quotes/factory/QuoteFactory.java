package tui.meta.challenge.quotes.factory;

import tui.meta.challenge.quotes.repository.model.QuoteEntity;

public class QuoteFactory {

    private static final String AUTHOR = "Steve Jobs";
    private static final String ID = "test-id-123";

    public static QuoteEntity getQuoteEntity() {
        return getQuoteEntity(ID, AUTHOR);
    }

    public static QuoteEntity getQuoteEntity(String id) {
        return generateQuoteEntity(id, AUTHOR);
    }

    public static QuoteEntity getQuoteEntity(String id, String author) {
        return generateQuoteEntity(id, author);
    }

    private static QuoteEntity generateQuoteEntity(String id, String author) {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setId(id);
        quoteEntity.setText("The only way to do great work is to love what you do.");
        quoteEntity.setAuthor(author);
        quoteEntity.setGenre("Inspirational");
        quoteEntity.setVersion(1);
        return quoteEntity;
    }

}
