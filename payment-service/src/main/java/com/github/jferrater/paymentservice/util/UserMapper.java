package com.github.jferrater.paymentservice.util;

import com.github.jferrater.paymentservice.dto.User;
import com.github.jferrater.paymentservice.entity.UserEntity;

public class UserMapper {

    public static User toDto(UserEntity userEntity) {
        User user = new User();
        user.setBalance(userEntity.getBalance());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setId(userEntity.getId().toString());
        user.setEmail(userEntity.getEmail());
        return user;
    }

    public static UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setBalance(user.getBalance());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }
}
