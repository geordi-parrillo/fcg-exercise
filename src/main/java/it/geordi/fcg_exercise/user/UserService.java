package it.geordi.fcg_exercise.user;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.geordi.fcg_exercise.user.dto.UserSaveRequest;
import it.geordi.fcg_exercise.user.exception.UserNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> search(String name, String surname) {
        return userRepository.findAll(Example.of(User.builder()
                .name(name)
                .surname(surname)
                .build()), Sort.by(Sort.Direction.ASC, "id"));
    }

    public User findById(@NotNull Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    public Long createOrUpdate(@NotNull UserSaveRequest saveRequest, Long id) {
        User user;
        if (id == null) {
            user = saveRequest.createEntity();
        } else {
            user = findById(id);
            saveRequest.updateEntity(user);
        }
        return userRepository.save(user).getId();
    }

    public void delete(@NotNull Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public void createFromCSV(MultipartFile file) {
        List<User> users = UserCSVHelper.getUsers(file);
        userRepository.saveAll(users);
    }
}
