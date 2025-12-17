package com.expense.authservice.controller;

import com.expense.authservice.entities.RefreshToken;
import com.expense.authservice.request.AuthRequestDTO;
import com.expense.authservice.request.RefreshTokenRequestDTO;
import com.expense.authservice.response.JwtResponseDTO;
import com.expense.authservice.services.JwtService;
import com.expense.authservice.services.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TokenController {

    private AuthenticationManager authenticationManager;
    private RefreshTokenService refreshTokenService;
    private JwtService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity<JwtResponseDTO> AuthenticateAndGetToken (@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );

            if (authentication.isAuthenticated()) {
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
                String jwtToken = jwtService.GenerateToken(authRequestDTO.getUsername());

                return new ResponseEntity<>(JwtResponseDTO
                        .builder()
                        .accessToken(jwtToken)
                        .token(refreshToken.getToken())
                        .build(), HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(new JwtResponseDTO(), HttpStatus.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new JwtResponseDTO(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new JwtResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("auth/v1/refreshToken")
    public ResponseEntity<JwtResponseDTO> refreshToken (@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        try {
            JwtResponseDTO response = refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                    .map(refreshTokenService :: verifyExpiration)
                    .map(RefreshToken :: getUserInfo)
                    .map(userInfo -> {
                        String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                        return JwtResponseDTO
                                .builder()
                                .accessToken(accessToken)
                                .token(refreshTokenRequestDTO.getToken())
                                .build();
                    })
                    .orElseThrow(() -> new RuntimeException("Refresh token is not in DB!"));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new JwtResponseDTO(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new JwtResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
