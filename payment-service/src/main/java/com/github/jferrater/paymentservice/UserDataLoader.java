package com.github.jferrater.paymentservice;

import com.github.jferrater.paymentservice.entity.UserEntity;
import com.github.jferrater.paymentservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        UserEntity jolly = new UserEntity(UUID.randomUUID(), "jolly.jae@gmail.com", "Jolly", "Jae", 7000.0);
        UserEntity keerthi = new UserEntity(UUID.randomUUID(), "keerthi.lee@gmail.com", "Keerthi", "Lee", 100.0);
        UserEntity wella = new UserEntity(UUID.randomUUID(), "wella.sky@gmail.com", "Wella", "Sky", 1200.0);
        UserEntity hinata = new UserEntity(UUID.randomUUID(), "hinata.shoyo@gmail.com", "Hinata", "Soyo", 500.0);
        userRepository.save(jolly).block();
        userRepository.save(keerthi).block();
        userRepository.save(wella).block();
        userRepository.save(hinata).block();

    }
}
