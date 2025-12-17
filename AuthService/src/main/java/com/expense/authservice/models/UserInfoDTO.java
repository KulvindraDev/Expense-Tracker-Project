package com.expense.authservice.models;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO {
    private String username;

    private String password;

    @JsonAlias("user_id")
    private String userId;

    @JsonAlias("first_name")
    private String firstName;

    @JsonAlias("last_name")
    private String lastName;

    @JsonAlias({"email_id", "email"})
    private String emailId;

    @JsonAlias({"phone_number", "phone"})
    private Long phoneNumber;
}
