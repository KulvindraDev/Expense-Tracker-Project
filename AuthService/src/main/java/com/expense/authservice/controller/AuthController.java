package com.expense.authservice.controller;

import com.expense.authservice.entities.RefreshToken;
import com.expense.authservice.models.UserInfoDTO;
import com.expense.authservice.response.JwtResponseDTO;
import com.expense.authservice.services.JwtService;
import com.expense.authservice.services.RefreshTokenService;
import com.expense.authservice.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    private JwtService jwtService;
    private RefreshTokenService refreshTokenService;
    private UserDetailsServiceImpl userDetailsService;


    @PostMapping("auth/v1/signup")
    public ResponseEntity<JwtResponseDTO> signUp (@RequestBody UserInfoDTO userInfoDto) {
        try {
            Boolean isSignedUp = userDetailsService.signupUser(userInfoDto);
            if (Boolean.FALSE.equals(isSignedUp)) {
                return new ResponseEntity<>(new JwtResponseDTO(), HttpStatus.BAD_REQUEST);
            }

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());
            return new ResponseEntity<>(JwtResponseDTO
                    .builder()
                    .accessToken(jwtToken)
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK
            );
        } catch (Exception e){
            return new ResponseEntity<>(new JwtResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
