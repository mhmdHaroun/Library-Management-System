package dev.mosaleh.Library.Management.System.dtos;

import lombok.Data;

@Data
public class PatronResponse {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
