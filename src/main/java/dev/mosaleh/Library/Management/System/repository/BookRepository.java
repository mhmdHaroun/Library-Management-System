package dev.mosaleh.Library.Management.System.repository;

import dev.mosaleh.Library.Management.System.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);
}
