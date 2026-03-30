package net.samitkumar.aws_lambda_spring_cloud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureRestTestClient
public class UserControllerTest {

    @Autowired
    RestTestClient restTestClient;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    // ------------------------------------------------------------------ GET all
    @Test
    void shouldGetAllUsers() {
        userRepository.saveAll(
                List.of(
                        new User(null, "Alice", 30),
                        new User(null, "Bob", 25)
                )
        );

        restTestClient.get().uri("/api/user")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<List<User>>() {
                })
                .value(users -> assertThat(users).hasSize(2));
    }

    @Test
    void shouldReturnEmptyListWhenNoUsers() {
        restTestClient.get().uri("/api/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<User>>() {
                })
                .value(users -> assertThat(users).isEmpty());
    }

    // ------------------------------------------------------------------ GET by id
    @Test
    void shouldGetUserById() {
        User saved = userRepository.save(new User(null, "Alice", 30));

        restTestClient.get().uri("/api/user/{id}", saved.id())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(saved);
    }

    @Test
    void shouldReturn404ForUnknownId() {
        restTestClient.get().uri("/api/user/9999")
                .exchange()
                .expectStatus().isNotFound();
    }

    // ------------------------------------------------------------------ GET filter
    @Test
    void shouldFilterByName() {
        userRepository.saveAll(List.of(
                new User(null, "Alice", 30),
                new User(null, "Bob", 25)
        ));

        restTestClient.get().uri("/api/user/filter?name=Alice")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<User>>() {
                })
                .value(users -> {
                    assertThat(users).hasSize(1);
                    assertThat(users.getFirst().name()).isEqualTo("Alice");
                });
    }

    @Test
    void shouldFilterByAge() {
        userRepository.saveAll(List.of(
                new User(null, "Alice", 30),
                new User(null, "Bob", 25)
        ));

        restTestClient.get().uri("/api/user/filter?age=25")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<User>>() {
                })
                .value(users -> {
                    assertThat(users).hasSize(1);
                    assertThat(users.getFirst().name()).isEqualTo("Bob");
                });
    }

    @Test
    void shouldFilterByNameAndAge() {
        userRepository.saveAll(List.of(
                new User(null, "Alice", 30),
                new User(null, "Alice", 25)
        ));

        restTestClient.get().uri("/api/user/filter?name=Alice&age=30")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<User>>() {
                })
                .value(users -> {
                    assertThat(users).hasSize(1);
                    assertThat(users.getFirst().age()).isEqualTo(30);
                });
    }

    @Test
    void shouldReturnAllUsersWhenNoFilterProvided() {
        userRepository.saveAll(List.of(
                new User(null, "Alice", 30),
                new User(null, "Bob", 25)
        ));

        restTestClient.get().uri("/api/user/filter")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<User>>() {
                })
                .value(users -> assertThat(users).hasSize(2));
    }

    // ------------------------------------------------------------------ POST
    @Test
    void shouldCreateUser() {
        restTestClient.post().uri("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new User(null, "Charlie", 28))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .value(u -> {
                    assertThat(u.id()).isNotNull();
                    assertThat(u.name()).isEqualTo("Charlie");
                    assertThat(u.age()).isEqualTo(28);
                });
    }

    // ------------------------------------------------------------------ PUT
    @Test
    void shouldReplaceUser() {
        User saved = userRepository.save(new User(null, "Alice", 30));

        restTestClient.put().uri("/api/user/{id}", saved.id())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new User(null, "Alice Updated", 31))
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(u -> {
                    assertThat(u.id()).isEqualTo(saved.id());
                    assertThat(u.name()).isEqualTo("Alice Updated");
                    assertThat(u.age()).isEqualTo(31);
                });
    }

    @Test
    void shouldReturn404WhenReplacingNonExistentUser() {
        restTestClient.put().uri("/api/user/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new User(null, "Ghost", 0))
                .exchange()
                .expectStatus().isNotFound();
    }

    // ------------------------------------------------------------------ PATCH
    @Test
    void shouldPatchUserName() {
        User saved = userRepository.save(new User(null, "Alice", 30));

        restTestClient.patch().uri("/api/user/{id}", saved.id())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("name", "Alice Patched"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(u -> {
                    assertThat(u.name()).isEqualTo("Alice Patched");
                    assertThat(u.age()).isEqualTo(30);   // unchanged
                });
    }

    @Test
    void shouldPatchUserAge() {
        User saved = userRepository.save(new User(null, "Bob", 25));

        restTestClient.patch().uri("/api/user/{id}", saved.id())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("age", 26))
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(u -> {
                    assertThat(u.name()).isEqualTo("Bob");  // unchanged
                    assertThat(u.age()).isEqualTo(26);
                });
    }

    @Test
    void shouldReturn404WhenPatchingNonExistentUser() {
        restTestClient.patch().uri("/api/user/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("name", "Ghost"))
                .exchange()
                .expectStatus().isNotFound();
    }

    // ------------------------------------------------------------------ DELETE
    @Test
    void shouldDeleteUser() {
        User saved = userRepository.save(new User(null, "Alice", 30));

        restTestClient.delete().uri("/api/user/{id}", saved.id())
                .exchange()
                .expectStatus().isNoContent();

        assertThat(userRepository.findById(saved.id())).isEmpty();
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentUser() {
        restTestClient.delete().uri("/api/user/9999")
                .exchange()
                .expectStatus().isNotFound();
    }
}
