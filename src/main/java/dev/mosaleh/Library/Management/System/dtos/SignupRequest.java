package dev.mosaleh.Library.Management.System.dtos;

import lombok.Data;

@Data
public class SignupRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
