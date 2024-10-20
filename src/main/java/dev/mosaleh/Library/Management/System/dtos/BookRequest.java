package dev.mosaleh.Library.Management.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequest {
    private String title;
    private String author;
    private int publicationYear;
    private double price;
    private long isbn;
}
