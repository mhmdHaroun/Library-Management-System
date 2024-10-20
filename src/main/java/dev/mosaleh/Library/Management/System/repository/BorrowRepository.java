package dev.mosaleh.Library.Management.System.repository;

import dev.mosaleh.Library.Management.System.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    Optional<Borrow> findById(Long id);

    Optional<Borrow> findByBookIdAndPatronId(Long bookId, Long patronId);

}
