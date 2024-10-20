package dev.mosaleh.Library.Management.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookResponse {
    private long id;
    private String title;
    private String author;
    private double price;
}
