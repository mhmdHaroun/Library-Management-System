package dev.mosaleh.Library.Management.System.dtos;


import lombok.Data;

@Data
public class PatronRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
