package dev.mosaleh.Library.Management.System.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;

    public JwtAuthenticationResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
    public JwtAuthenticationResponse(String token) {
        this.token = token;
        this.refreshToken = null;
    }
}
