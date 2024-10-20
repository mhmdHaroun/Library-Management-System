package dev.mosaleh.Library.Management.System.dtos;


import lombok.Data;

@Data
public class SigninRequest {
    private String email;
    private String password;

}
