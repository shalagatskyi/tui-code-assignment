package tui.meta.challenge.quotes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tui.meta.challenge.quotes.repository.model.QuoteEntity;

import java.util.List;

public interface QuoteRepository extends MongoRepository<QuoteEntity, String> {

    @Query("{$text: { $search: ?0, $caseSensitive: false}}")
    List<QuoteEntity> getQuoteByAuthorFullTextSearch(String author);
}
