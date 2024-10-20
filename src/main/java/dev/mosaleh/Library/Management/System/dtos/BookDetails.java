package dev.mosaleh.Library.Management.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BookDetails {
    private long ID;
    private String title;
    private String author;
    private short publicationYear;
    private long ISBN;
    private float price;

}
