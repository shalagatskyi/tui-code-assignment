package tui.meta.challenge.quotes.repository.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "quotes")
@Getter
@Setter
public class QuoteEntity {
    @MongoId
    private String id;

    @Field(name = "quoteText")
    private String text;

    @Field(name = "quoteAuthor")
    // we can have just @Indexed but text index is preferable for search functionality
    @TextIndexed
    private String author;

    @Field(name = "quoteGenre")
    private String genre;

    @Field(name = "__v")
//    @Version
//    can add optimistic locking in case there would be a write/update functionality
    private int version;
}
