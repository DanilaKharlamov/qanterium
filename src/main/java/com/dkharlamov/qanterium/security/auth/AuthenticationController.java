package com.dkharlamov.qanterium.security.auth;

import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.security.auth.dto.AuthenticationRequest;
import com.dkharlamov.qanterium.security.auth.dto.AuthenticationResponse;
import com.dkharlamov.qanterium.security.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "User registration and authentication service")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403",
                    description = "Registration error",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Successful registration",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));

    }

    @PostMapping("/authenticate")
    @Operation(summary = "User authentication", description = "User authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403",
                    description = "authentication error",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Successful authentication",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

}
