package dev.mosaleh.Library.Management.System.controller;

import dev.mosaleh.Library.Management.System.service.BorrowService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BorrowControllerTest {

    @Mock
    private BorrowService borrowService;

    @InjectMocks
    private BorrowController borrowController;

    public BorrowControllerTest() {
        MockitoAnnotations.openMocks(this);  // Initializes mocks and inject them
    }

    @Test
    void testBorrowBook() {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        // When
        ResponseEntity<String> response = borrowController.borrowBook(bookId, patronId);

        // Then
        verify(borrowService, times(1)).borrowBook(bookId, patronId);
        assertEquals("Book borrowed successfully", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testReturnBook() {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        // When
        ResponseEntity<String> response = borrowController.returnBook(bookId, patronId);

        // Then
        verify(borrowService, times(1)).returnBook(bookId, patronId);
        assertEquals("Book returned successfully", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
