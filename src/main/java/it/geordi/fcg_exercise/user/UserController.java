package it.geordi.fcg_exercise.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import it.geordi.fcg_exercise.user.dto.UserSaveRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> searchUsers(@RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "surname", required = false) String surname) {
        return ResponseEntity.ok(userService.search(name, surname));
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserSaveRequest saveRequest) {
        Long id = userService.createOrUpdate(saveRequest, null);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserSaveRequest saveRequest) {
        userService.createOrUpdate(saveRequest, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("upload")
    public ResponseEntity<String> createFromCSV(@RequestParam("file") MultipartFile file) {
        if (UserCSVHelper.hasNotCSVFormat(file))
            return ResponseEntity.badRequest().body("CSV file required!");

        try {
            userService.createFromCSV(file);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Could not upload the file: " + file.getOriginalFilename());
        }
        return ResponseEntity.ok("Uploaded the file successfully: " + file.getOriginalFilename());
    }
}
