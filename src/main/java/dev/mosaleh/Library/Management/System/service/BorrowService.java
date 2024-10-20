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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BorrowService {
    private static BorrowRepository borrowRepository;
    private static BookRepository bookRepository;
    private static PatronRepository patronRepository;

    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found"));

        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setPatron(patron);
        borrowRepository.save(borrow);
    }

    public void returnBook(Long bookId, Long patronId) {
        Borrow borrow = borrowRepository.findByBookIdAndPatronId(bookId, patronId)
                .orElseThrow(() -> new BorrowRecordNotFoundException("Borrow record not found"));

        // Additional logic to check if the book can be returned (e.g., was it actually borrowed)
        // Remove the borrow record or mark as returned
        borrowRepository.delete(borrow);
    }

}
