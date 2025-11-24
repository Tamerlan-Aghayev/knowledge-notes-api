## Note App

Minimal Spring Boot API that manages notes entirely in memory.

- `POST /api/notes` create a note
- `GET /api/notes` list notes
- `GET /api/notes/{id}` fetch single note
- `DELETE /api/notes/{id}` remove note

### Getting Started

```
cd note-app
./mvnw spring-boot:run
```

Then hit `http://localhost:8080/api/notes`.


