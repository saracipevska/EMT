package mk.ukim.finki.wp.emtlab.web.controller;

import mk.ukim.finki.wp.emtlab.model.dto.CreateBookDTO;
import mk.ukim.finki.wp.emtlab.model.dto.DisplayBookDTO;
import mk.ukim.finki.wp.emtlab.service.application.BookApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookApplicationService bookApplicationService;

    public BookController(BookApplicationService bookApplicationService) {
        this.bookApplicationService = bookApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<DisplayBookDTO>> findAllBooks() {
        return ResponseEntity.ok(bookApplicationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DisplayBookDTO>> findBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookApplicationService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<DisplayBookDTO> createBook(@RequestBody CreateBookDTO createBookDTO) {
        return ResponseEntity.ok(bookApplicationService.create(createBookDTO));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayBookDTO> updateBook(@PathVariable Long id, @RequestBody CreateBookDTO createBookDTO) {
        return bookApplicationService
                .update(id, createBookDTO)
                .map(it -> ResponseEntity.ok().body(it))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/rent/{id}")
    public ResponseEntity<Optional<DisplayBookDTO>> rentBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookApplicationService.rent(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return bookApplicationService.deleteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
