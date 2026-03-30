package net.samitkumar.aws_lambda_spring_cloud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    List<User> getByFilter(@RequestParam(required = false) String name,
                           @RequestParam(required = false) Integer age) {
        return userRepository.findByFilter(name, age);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        User updated = new User(id, user.name(), user.age());
        return ResponseEntity.ok(userRepository.save(updated));
    }

    @PatchMapping("/{id}")
    ResponseEntity<User> patch(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        Optional<User> existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User current = existing.get();
        String name = fields.containsKey("name") ? (String) fields.get("name") : current.name();
        int age = fields.containsKey("age") ? (Integer) fields.get("age") : current.age();
        User patched = new User(id, name, age);
        return ResponseEntity.ok(userRepository.save(patched));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
