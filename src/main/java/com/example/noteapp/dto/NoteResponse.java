package com.example.noteapp.dto;

import java.time.Instant;

/**
 * DTO returned to API consumers to describe a stored note.
 */
public record NoteResponse(long id, String title, String content, Instant createdAt) {
}


