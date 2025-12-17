package com.expense.userservice.deserializer;

import com.expense.userservice.models.UserInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.apache.kafka.common.serialization.Deserializer;

public class UserInfoDeserializer implements Deserializer<UserInfoDTO> {
    private final ObjectMapper mapper;

    public UserInfoDeserializer() {
        this.mapper = new ObjectMapper();
        this.mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @Override
    public UserInfoDTO deserialize (String topic, byte[] data) {
        if (data == null) return null;
        try {
            return mapper.readValue(data, UserInfoDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
