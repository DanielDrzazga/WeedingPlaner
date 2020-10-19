package weddingplanner.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weddingplanner.application.models.Role;

/**
 * Create by Daniel Drzazga on 16.10.2020
 **/

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);
}
