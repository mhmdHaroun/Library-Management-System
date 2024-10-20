package dev.mosaleh.Library.Management.System.controller;


import dev.mosaleh.Library.Management.System.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowService.borrowBook(bookId, patronId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowService.returnBook(bookId, patronId);
        return ResponseEntity.ok("Book returned successfully");
    }

}
