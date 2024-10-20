package dev.mosaleh.Library.Management.System.service;

import dev.mosaleh.Library.Management.System.exception.BookNotFoundException;
import dev.mosaleh.Library.Management.System.exception.PatronNotFoundException;
import dev.mosaleh.Library.Management.System.exception.BorrowRecordNotFoundException;
import dev.mosaleh.Library.Management.System.model.Book;
import dev.mosaleh.Library.Management.System.model.Borrow;
import dev.mosaleh.Library.Management.System.model.Patron;
import dev.mosaleh.Library.Management.System.repository.BookRepository;
import dev.mosaleh.Library.Management.System.repository.BorrowRepository;
import dev.mosaleh.Library.Management.System.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BorrowServiceTest {

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowService borrowService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initializes mocks
    }

    @Test
    void testBorrowBook_Success() {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        Book book = new Book();
        Patron patron = new Patron();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));

        // When
        borrowService.borrowBook(bookId, patronId);

        // Then
        verify(borrowRepository, times(1)).save(any(Borrow.class));
    }

    @Test
    void testBorrowBook_BookNotFound() {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> borrowService.borrowBook(bookId, patronId));
        verify(borrowRepository, times(0)).save(any(Borrow.class));
    }

    @Test
    void testBorrowBook_PatronNotFound() {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        Book book = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(PatronNotFoundException.class, () -> borrowService.borrowBook(bookId, patronId));
        verify(borrowRepository, times(0)).save(any(Borrow.class));
    }

    @Test
    void testReturnBook_Success() {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        Borrow borrow = new Borrow();
        when(borrowRepository.findByBookIdAndPatronId(bookId, patronId)).thenReturn(Optional.of(borrow));

        // When
        borrowService.returnBook(bookId, patronId);

        // Then
        verify(borrowRepository, times(1)).delete(borrow);
    }

    @Test
    void testReturnBook_BorrowRecordNotFound() {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        when(borrowRepository.findByBookIdAndPatronId(bookId, patronId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BorrowRecordNotFoundException.class, () -> borrowService.returnBook(bookId, patronId));
        verify(borrowRepository, times(0)).delete(any(Borrow.class));
    }
}

