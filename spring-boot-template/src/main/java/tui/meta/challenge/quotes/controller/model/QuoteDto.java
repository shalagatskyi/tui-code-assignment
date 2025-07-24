package tui.meta.challenge.quotes.controller.model;

import tui.meta.challenge.quotes.repository.model.QuoteEntity;

public record QuoteDto(
    String id,
    String text,
    String author,
    String genre) {

    public QuoteDto(QuoteEntity entity) {
        this(entity.getId(), entity.getText(), entity.getAuthor(), entity.getGenre());
    }
}
