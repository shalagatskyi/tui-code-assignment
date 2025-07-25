package tui.meta.challenge.repository.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@MappedEntity(value = "quotes")
@Serdeable
data class QuoteEntity(
    @Id
    var id: String,

    var quoteText: String,

    var quoteAuthor: String,

    var quoteGenre: String,

    var version: Int? = null
)