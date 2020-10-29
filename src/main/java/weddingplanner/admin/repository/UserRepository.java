package weddingplanner.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weddingplanner.application.models.User;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

}
