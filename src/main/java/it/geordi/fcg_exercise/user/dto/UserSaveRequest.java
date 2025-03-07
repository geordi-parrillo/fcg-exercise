package it.geordi.fcg_exercise.user.dto;

import it.geordi.fcg_exercise.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserSaveRequest(
        @NotEmpty @Size(max = 100) String name,
        @NotEmpty @Size(max = 100) String surname,
        @NotEmpty @Size(max = 100) String email,
        @Size(max = 255) String address) {

    public User createEntity() {
        return User.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .address(address)
                .build();
    }

    public User updateEntity(User user) {
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setAddress(address);
        return user;
    }
}
