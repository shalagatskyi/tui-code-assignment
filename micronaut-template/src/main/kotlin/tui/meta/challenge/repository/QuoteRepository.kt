package tui.meta.challenge.repository

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import tui.meta.challenge.repository.model.QuoteEntity

@MongoRepository
interface QuoteRepository : CrudRepository<QuoteEntity, String> {
    fun findByQuoteAuthor(author: String): List<QuoteEntity>
}