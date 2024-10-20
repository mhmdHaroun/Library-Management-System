package dev.mosaleh.Library.Management.System.controller;

import dev.mosaleh.Library.Management.System.dtos.BookRequest;
import dev.mosaleh.Library.Management.System.dtos.BookResponse;
import dev.mosaleh.Library.Management.System.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private BookRequest bookRequest;
    private BookResponse bookResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock input data
        bookRequest = new BookRequest();
        bookRequest.setTitle("Test Book");
        bookRequest.setAuthor("Test Author");
        bookRequest.setPrice(19.99);
        bookRequest.setPublicationYear(2020);
        bookRequest.setIsbn(1234567890L);

        bookResponse = new BookResponse();
        bookResponse.setId(1);
        bookResponse.setTitle("Test Book");
        bookResponse.setAuthor("Test Author");
        bookResponse.setPrice(19.99);
    }

    @Test
    void getBooks() {
        // Given
        Pageable pageable = Pageable.unpaged();
        List<BookResponse> bookResponses = List.of(bookResponse);
        Page<BookResponse> page = new PageImpl<>(bookResponses, pageable, 1);
        when(bookService.getAllBooks(pageable)).thenReturn(page);

        // When
        Page<BookResponse> result = bookController.getBooks(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Book", result.getContent().get(0).getTitle());
    }

    @Test
    void getBookDetails() {
        // Given
        when(bookService.getBookDetails(1L)).thenReturn(bookResponse);

        // When
        BookResponse result = bookController.bookDetails(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
    }

    @Test
    void addBook() {
        // Given
        when(bookService.addBook(bookRequest)).thenReturn(bookResponse);

        // When
        ResponseEntity<BookResponse> result = bookController.addBook(bookRequest);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Test Book", result.getBody().getTitle());
    }

    @Test
    void updateBook() {
        // Given
        when(bookService.updateBook(1L, bookRequest)).thenReturn(bookResponse);

        // When
        ResponseEntity<BookResponse> result = bookController.updateBook(1L, bookRequest);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Test Book", result.getBody().getTitle());
    }

    @Test
    void deleteBook() {
        // Given
        doNothing().when(bookService).deleteBook(1L);

        // When
        ResponseEntity<Void> result = bookController.deleteBook(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}
