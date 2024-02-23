package com.github.jferrater.paymentservice.util;

import com.github.jferrater.paymentservice.dto.User;
import com.github.jferrater.paymentservice.entity.UserEntity;

public class UserMapper {

    public static User toDto(UserEntity userEntity) {
        User user = new User();
        user.setBalance(userEntity.getBalance());
        user.setId(userEntity.getId().toString());
        user.setEmail(userEntity.getEmail());
        return user;
    }
}
