package com.expense.userservice.models;

import com.expense.userservice.entities.UserInfo;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;


    @JsonProperty("email_id")
    @JsonAlias({"emailId", "email"})
    private String emailId;

    @JsonProperty("phone_number")
    @JsonAlias({"phoneNumber", "phone"})
    private Long phoneNumber;

    @JsonProperty("profile_pic")
    private String profilePic;

    public UserInfo transformToUserInfo() {

        return UserInfo.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .emailId(emailId)
                .phoneNumber(phoneNumber)
                .profilePic(profilePic)
                .build();
    }
}
