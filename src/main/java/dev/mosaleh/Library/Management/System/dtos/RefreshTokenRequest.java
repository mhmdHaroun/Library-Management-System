package dev.mosaleh.Library.Management.System.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RefreshTokenRequest {
    private String token;

}
