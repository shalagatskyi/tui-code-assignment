package tui.meta.challenge.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import jakarta.validation.constraints.Size
import tui.meta.challenge.controller.model.QuoteDto
import tui.meta.challenge.service.QuoteService

@Controller(value = "/api/v1/quotes")
class QuoteController(
    private val quoteServiceImpl: QuoteService
) {

    @Get(uri = "/{id}", produces = [MediaType.APPLICATION_JSON])
    fun getQuote(@PathVariable id: String): QuoteDto = quoteServiceImpl.getQuoteById(id)

    @Get(produces = [MediaType.APPLICATION_JSON])
    fun getQuotesWithSearch(
        @QueryValue("author") @Size(
            min = 2,
            max = 100,
            message = "Author name must be between 2 and 100 characters."
        ) author: String?
    ): List<QuoteDto> {
        if (author != null) {
            return quoteServiceImpl.getQuotesByAuthor(author)
        }

        return quoteServiceImpl.getAllQuotes()
    }

}