package dev.mosaleh.Library.Management.System.service;

import dev.mosaleh.Library.Management.System.dtos.BookRequest;
import dev.mosaleh.Library.Management.System.dtos.BookResponse;
import dev.mosaleh.Library.Management.System.model.Book;
import dev.mosaleh.Library.Management.System.exception.BookNotFoundException;
import dev.mosaleh.Library.Management.System.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(19.99);
        book.setPublicationYear(2020);
        book.setISBN(1234567890L);

        bookRequest = new BookRequest();
        bookRequest.setTitle("Test Book");
        bookRequest.setAuthor("Test Author");
        bookRequest.setPrice(19.99);
        bookRequest.setPublicationYear(2020);
        bookRequest.setIsbn(1234567890L);
    }

    @Test
    void getAllBooks() {
        // Given
        List<Book> books = List.of(book);
        Pageable pageable = Pageable.unpaged();
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        // When
        Page<BookResponse> result = bookService.getAllBooks(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Book", result.getContent().get(0).getTitle());
    }

    @Test
    void getBookDetails_whenBookExists() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        BookResponse result = bookService.getBookDetails(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals(1L, result.getId());
    }

    @Test
    void getBookDetails_whenBookDoesNotExist() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.getBookDetails(1L));
    }

    @Test
    void addBook() {
        // Given
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        BookResponse result = bookService.addBook(bookRequest);

        // Then
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
    }

    @Test
    void updateBook_whenBookExists() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookRequest.setTitle("Updated Book");

        // When
        BookResponse result = bookService.updateBook(1L, bookRequest);

        // Then
        assertNotNull(result);
        assertEquals("Updated Book", result.getTitle());
    }

    @Test
    void updateBook_whenBookDoesNotExist() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, bookRequest));
    }

    @Test
    void deleteBook_whenBookExists() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        bookService.deleteBook(1L);

        // Then
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBook_whenBookDoesNotExist() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}
