package com.example.noteapp.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Simple in-memory representation of a note.
 */
public class Note {

    private final long id;
    private final String title;
    private final String content;
    private final Instant createdAt;

    public Note(long id, String title, String content, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Note note = (Note) o;
        return id == note.id
                && Objects.equals(title, note.title)
                && Objects.equals(content, note.content)
                && Objects.equals(createdAt, note.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createdAt);
    }
}


