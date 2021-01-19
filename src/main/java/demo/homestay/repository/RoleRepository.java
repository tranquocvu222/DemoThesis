package demo.homestay.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.homestay.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findRoleByRoleName(String roleName);
}
