package tui.meta.challenge.service

import jakarta.inject.Singleton
import tui.meta.challenge.controller.exceptions.QuoteNotFoundException
import tui.meta.challenge.controller.model.QuoteDto
import tui.meta.challenge.repository.QuoteRepository

@Singleton
class QuoteServiceImpl(
    private val quoteRepository: QuoteRepository
) : QuoteService {

    override fun getQuoteById(id: String): QuoteDto = quoteRepository.findById(id)
        .orElseThrow { QuoteNotFoundException("there is no such quote with id=${id}") }
        .let(::QuoteDto)

    override fun getAllQuotes(): List<QuoteDto> =
        quoteRepository.findAll().map(::QuoteDto)

    // can be improved because right now it's just exact match case
    override fun getQuotesByAuthor(author: String): List<QuoteDto> {
        val sanitizedAuthor = author.replace(REGEX, "")
        return quoteRepository.findByQuoteAuthor(sanitizedAuthor).map(::QuoteDto)
    }

    companion object {
        val REGEX = Regex("[^a-zA-Z0-9\\s]")
    }
}