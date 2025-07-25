package tui.meta.challenge.service

import tui.meta.challenge.controller.model.QuoteDto

interface QuoteService {
    fun getQuoteById(id: String): QuoteDto
    fun getAllQuotes(): List<QuoteDto>
    fun getQuotesByAuthor(author: String): List<QuoteDto>
}