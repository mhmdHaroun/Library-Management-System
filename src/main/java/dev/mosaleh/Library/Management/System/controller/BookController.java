package dev.mosaleh.Library.Management.System.controller;

import dev.mosaleh.Library.Management.System.dtos.BookRequest;
import dev.mosaleh.Library.Management.System.dtos.BookResponse;
import dev.mosaleh.Library.Management.System.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<BookResponse> getBooks(Pageable pageable) {
        return bookService.getAllBooks(pageable);  // Call service method to retrieve books with pagination.
    }

    @GetMapping("/{id}")
    public BookResponse bookDetails(@PathVariable Long id) {
        return bookService.getBookDetails(id);  // Call service method to get a book by ID.
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        BookResponse savedBook = bookService.addBook(bookRequest);  // Call service to add a new book.
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        BookResponse updatedBook = bookService.updateBook(id, bookRequest);  // Call service to update the book.
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);  // Call service to delete the book by ID.
        return ResponseEntity.noContent().build();  // Return status 204 (No Content) after successful deletion.
    }
}
