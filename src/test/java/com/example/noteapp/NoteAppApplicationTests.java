package com.example.noteapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteAppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAndRetrieveNote() throws Exception {
        String payload = """
                {"title":"First","content":"Remember the milk"}
                """;

        String createResponse = mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode created = objectMapper.readTree(createResponse);
        long noteId = created.get("id").asLong();
        assertThat(created.get("title").asText()).isEqualTo("First");

        mockMvc.perform(get("/api/notes/" + noteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("First"))
                .andExpect(jsonPath("$.content").value("Remember the milk"));
    }

    @Test
    void deleteNoteRemovesIt() throws Exception {
        String payload = """
                {"title":"Temp","content":"Disposable"}
                """;

        String response = mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long noteId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/notes/" + noteId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/notes/" + noteId))
                .andExpect(status().isNotFound());
    }
}

