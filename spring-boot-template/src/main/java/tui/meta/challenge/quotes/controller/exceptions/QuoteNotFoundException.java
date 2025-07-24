package tui.meta.challenge.quotes.controller.exceptions;

public class QuoteNotFoundException extends RuntimeException {

    public QuoteNotFoundException(String message) {
        super((message));
    }
}
