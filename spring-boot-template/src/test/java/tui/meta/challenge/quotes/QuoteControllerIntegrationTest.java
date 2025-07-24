package tui.meta.challenge.quotes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tui.meta.challenge.quotes.controller.model.ErrorResponse;
import tui.meta.challenge.quotes.controller.model.QuoteDto;
import tui.meta.challenge.quotes.repository.QuoteRepository;
import tui.meta.challenge.quotes.repository.model.QuoteEntity;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tui.meta.challenge.quotes.factory.QuoteFactory.getQuoteEntity;


public class QuoteControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    void shouldReturnSingleQuoteById() throws Exception {
        String testId = getNextId();
        QuoteEntity quoteEntity = getQuoteEntity(testId);
        quoteRepository.save(quoteEntity);

        QuoteDto expectedDto = new QuoteDto(quoteEntity);

        mockMvc.perform(get("/api/v1/quotes/" + testId)
                        .param("id", testId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void shouldReturnEmptyListWhenQuoteByIdNotFound() throws Exception {
        String nonExistentId = "non-existent-id";

        var expectedError = new ErrorResponse(404, "quote with given id:non-existent-id is missing");

        mockMvc.perform(get("/api/v1/quotes/" + nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedError)));
    }

    @Test
    void shouldReturnListOfQuotesByAuthor() throws Exception {
        String testAuthor = "Famous Author";
        String id1 = getNextId();
        String id2 = getNextId();

        QuoteEntity entity1 = getQuoteEntity(id1, testAuthor);
        QuoteEntity entity2 = getQuoteEntity(id2, testAuthor);

        quoteRepository.saveAll(List.of(entity1, entity2));

        QuoteDto expectedDto1 = new QuoteDto(entity1);
        QuoteDto expectedDto2 = new QuoteDto(entity2);

        mockMvc.perform(get("/api/v1/quotes")
                        .param("author", testAuthor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expectedDto1, expectedDto2))));
    }

    @Test
    void shouldReturnEmptyListWhenNoQuotesForAuthor() throws Exception {
        String nonExistentAuthor = "Unknown Author";

        mockMvc.perform(get("/api/v1/quotes")
                        .param("author", nonExistentAuthor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldReturnBadRequestWhenAuthorNameIsTooShort() throws Exception {
        var expectedError = new ErrorResponse(400, "getQuotesWithSearch.author: Author name must be between 2 and 100 characters.");
        mockMvc.perform(get("/api/v1/quotes")
                        .param("author", "U")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedError)));
    }

    @Test
    void shouldReturnAllQuotes() throws Exception {
        String id1 = getNextId();
        String id2 = getNextId();
        String id3 = getNextId();

        QuoteEntity entity1 = getQuoteEntity(id1);
        QuoteEntity entity2 = getQuoteEntity(id2);
        QuoteEntity entity3 = getQuoteEntity(id3);

        quoteRepository.saveAll(List.of(entity1, entity2, entity3));

        QuoteDto expectedDto1 = new QuoteDto(entity1);
        QuoteDto expectedDto2 = new QuoteDto(entity2);
        QuoteDto expectedDto3 = new QuoteDto(entity3);

        mockMvc.perform(get("/api/v1/quotes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expectedDto1, expectedDto2, expectedDto3))));
    }
}
