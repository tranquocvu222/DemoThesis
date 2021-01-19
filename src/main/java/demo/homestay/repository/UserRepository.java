package demo.homestay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.homestay.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUserName(String userName);
	
}
