package com.example.noteapp.service;

import com.example.noteapp.dto.NoteRequest;
import com.example.noteapp.dto.NoteResponse;
import com.example.noteapp.model.Note;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteService {

    private final Map<Long, Note> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public NoteResponse create(NoteRequest request) {
        long id = idGenerator.getAndIncrement();
        Note note = new Note(id, request.title(), request.content(), Instant.now());
        storage.put(id, note);
        return toResponse(note);
    }

    public List<NoteResponse> findAll() {
        return storage.values().stream()
                .sorted((a, b) -> Long.compare(a.getId(), b.getId()))
                .map(this::toResponse)
                .toList();
    }

    public Optional<NoteResponse> findById(long id) {
        return Optional.ofNullable(storage.get(id))
                .map(this::toResponse);
    }

    public boolean delete(long id) {
        return storage.remove(id) != null;
    }

    private NoteResponse toResponse(Note note) {
        return new NoteResponse(note.getId(), note.getTitle(), note.getContent(), note.getCreatedAt());
    }
}


