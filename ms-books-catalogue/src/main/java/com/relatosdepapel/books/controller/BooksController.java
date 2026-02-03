package com.relatosdepapel.books.controller;

import com.relatosdepapel.books.controller.model.BookDto;
import com.relatosdepapel.books.controller.model.BookRequest;
import com.relatosdepapel.books.data.model.Book;
import com.relatosdepapel.books.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Tag(name = "Books", description = "API para gestionar el catálogo de libros")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BooksController {


    private final BookService service;


    @Operation(summary = "Buscar libros", description = "Buscar libros con filtros opcionales")
    @ApiResponse(responseCode = "200", description = "Lista de libros encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @Parameter(name = "params", description = "Parámetros de consulta: title, author, category, visible", required = false)
    @GetMapping
    public List<Book> search(@RequestParam(required = false) Map<String, String> params) {
        Map<String, String> filters = params == null ? new HashMap<>() : new HashMap<>(params);
        filters.putIfAbsent("visible", "true");
        return service.search(filters);
    }

    @Operation(summary = "Crear libro", description = "Crea un nuevo libro")
    @ApiResponse(responseCode = "201", description = "Libro creado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Payload del libro", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
    )
    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookRequest request) {
        Book saved = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Obtener libro por id", description = "Devuelve un libro por su identificador")
    @ApiResponse(responseCode = "200", description = "Libro encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @Parameter(name = "id", description = "ID del libro", required = true)
    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Actualizar libro (reemplazo completo)", description = "Actualiza completamente un libro existente")
    @ApiResponse(responseCode = "200", description = "Libro actualizado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Payload del libro para actualizar", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
    )
    @Parameter(name = "id", description = "ID del libro", required = true)
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody BookDto request) {
        return service.update(id, request);
    }

    @Operation(summary = "Actualización parcial de libro", description = "Actualiza campos permitidos del libro")
    @ApiResponse(responseCode = "200", description = "Libro parcialmente actualizado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Campos a actualizar", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
    )
    @Parameter(name = "id", description = "ID del libro", required = true)
    @PatchMapping("/{id}")
    public ResponseEntity<Book> partialUpdate(@PathVariable Long id, @RequestBody BookDto request) {
        Book updated = service.patch(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar libro", description = "Elimina un libro por id")
    @ApiResponse(responseCode = "204", description = "Libro eliminado")
    @Parameter(name = "id", description = "ID del libro", required = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
