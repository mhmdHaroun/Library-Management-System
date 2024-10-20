package dev.mosaleh.Library.Management.System.controller;

import dev.mosaleh.Library.Management.System.service.PatronService;
import dev.mosaleh.Library.Management.System.dtos.PatronRequest;
import dev.mosaleh.Library.Management.System.dtos.PatronResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/patrons")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatronController {
    private final PatronService patronService;

    @GetMapping
    public Page<PatronResponse> getAllPatrons(Pageable pageable) {
        return patronService.getAllPatrons(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronResponse> getPatronById(@PathVariable Long id) {
        PatronResponse patronResponse = patronService.getPatronById(id);
        return ResponseEntity.ok(patronResponse);
    }

    @PostMapping
    public ResponseEntity<PatronResponse> addPatron(@RequestBody PatronRequest patronRequest) {
        PatronResponse patronResponse = patronService.addPatron(patronRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(patronResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronResponse> updatePatron(@PathVariable Long id, @RequestBody PatronRequest patronRequest) {
        PatronResponse updatedPatron = patronService.updatePatron(id, patronRequest);
        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();  // Return status 204 (No Content)
    }
}