package tui.meta.challenge.quotes.mapping;

import org.springframework.stereotype.Component;
import tui.meta.challenge.quotes.controller.model.QuoteDto;
import tui.meta.challenge.quotes.repository.model.QuoteEntity;

import java.util.function.Function;

@Component
public class QuoteEntityToDtoFunc implements Function<QuoteEntity, QuoteDto> {
    @Override
    public QuoteDto apply(QuoteEntity quoteEntity) {
        return new QuoteDto(
                quoteEntity.getId(),
                quoteEntity.getText(),
                quoteEntity.getAuthor(),
                quoteEntity.getGenre()
        );
    }
}
