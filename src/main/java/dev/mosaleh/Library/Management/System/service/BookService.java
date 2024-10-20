package dev.mosaleh.Library.Management.System.service;

import dev.mosaleh.Library.Management.System.dtos.BookRequest;
import dev.mosaleh.Library.Management.System.dtos.BookResponse;
import dev.mosaleh.Library.Management.System.model.Book;
import dev.mosaleh.Library.Management.System.exception.BookNotFoundException;
import dev.mosaleh.Library.Management.System.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        return books.map(book -> new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice()
        ));
    }

    public BookResponse getBookDetails(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();

    }

    public BookResponse addBook(BookRequest bookRequest) {

        Book newBook = Book.builder()
                .title(bookRequest.getTitle())
                .price(bookRequest.getPrice())
                .ISBN(bookRequest.getIsbn())
                .publicationYear(bookRequest.getPublicationYear())
                .build();

        Book savedBook = bookRepository.save(newBook);

        return BookResponse.builder()
                .id(savedBook.getId())
                .author(savedBook.getAuthor())
                .title(savedBook.getTitle())
                .price(savedBook.getPrice())
                .build();
    }

    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Optional<Book> existingBookOpt = Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found")));

        Book existingBook = existingBookOpt.get();

        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setPublicationYear(bookRequest.getPublicationYear());
        existingBook.setPrice(bookRequest.getPrice());

        Book updatedBook = bookRepository.save(existingBook);

        return BookResponse.builder()
                .id(updatedBook.getId())
                .title(updatedBook.getTitle())
                .author(updatedBook.getAuthor())
                .price(updatedBook.getPrice())
                .build();
    }

    public void deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));

        bookRepository.delete(existingBook);
    }
}
