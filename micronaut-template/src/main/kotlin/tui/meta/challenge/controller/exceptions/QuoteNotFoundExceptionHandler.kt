package tui.meta.challenge.controller.exceptions

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Singleton
class QuoteNotFoundExceptionHandler : ExceptionHandler<QuoteNotFoundException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>, exception: QuoteNotFoundException): HttpResponse<*> {
        return HttpResponse
            .notFound(mapOf("error" to exception.message))
    }
}