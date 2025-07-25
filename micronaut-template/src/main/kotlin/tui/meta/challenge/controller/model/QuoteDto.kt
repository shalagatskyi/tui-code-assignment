package tui.meta.challenge.controller.model

import io.micronaut.serde.annotation.Serdeable
import tui.meta.challenge.repository.model.QuoteEntity

@Serdeable
data class QuoteDto(
    val id: String,
    val text: String,
    val author: String,
    val genre: String
) {
    constructor(entity: QuoteEntity) : this(
        id = entity.id,
        text = entity.quoteText,
        author = entity.quoteAuthor,
        genre = entity.quoteGenre
    )
}