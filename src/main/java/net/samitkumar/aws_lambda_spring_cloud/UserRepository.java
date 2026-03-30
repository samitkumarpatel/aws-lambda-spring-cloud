package net.samitkumar.aws_lambda_spring_cloud;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

interface UserRepository extends ListCrudRepository<User, Long> {

    @Query("""
            SELECT *
            FROM "user"
            WHERE (:name IS NULL OR name = :name)
              AND (:age  IS NULL OR age  = :age)
            """)
    List<User> findByFilter(String name, Integer age);
}
