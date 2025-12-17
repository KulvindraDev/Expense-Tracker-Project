package com.expense.userservice.services;

import com.expense.userservice.entities.UserInfo;
import com.expense.userservice.models.UserInfoDTO;
import com.expense.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    public UserInfoDTO createOrUpdateUser(UserInfoDTO userInfoDto){
        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId())
                .orElse(null);

        UserInfo userToSave = userInfo != null ? userInfo : new UserInfo();
        userToSave.setUserId(userInfoDto.getUserId());
        userToSave.setFirstName(userInfoDto.getFirstName());
        userToSave.setLastName(userInfoDto.getLastName());
        userToSave.setEmailId(userInfoDto.getEmailId());
        userToSave.setPhoneNumber(userInfoDto.getPhoneNumber());
        userToSave.setProfilePic(userInfoDto.getProfilePic());

        UserInfo savedUser = userRepository.save(userToSave);

        return new UserInfoDTO(
                savedUser.getUserId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmailId(),
                savedUser.getPhoneNumber(),
                savedUser.getProfilePic()
        );
    }

    public UserInfoDTO getUser(UserInfoDTO userInfoDto) throws Exception{
        Optional<UserInfo> userInfoDtoOpt = userRepository.findByUserId(userInfoDto.getUserId());
        if(userInfoDtoOpt.isEmpty()){
            throw new Exception("User not found");
        }
        UserInfo userInfo = userInfoDtoOpt.get();
        return new UserInfoDTO(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getEmailId(),
                userInfo.getPhoneNumber(),
                userInfo.getProfilePic()
        );
    }
}
