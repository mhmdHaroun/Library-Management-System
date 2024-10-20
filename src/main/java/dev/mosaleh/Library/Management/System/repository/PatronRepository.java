package dev.mosaleh.Library.Management.System.repository;

import dev.mosaleh.Library.Management.System.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findById(Long id);
}
