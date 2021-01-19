package demo.homestay.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import demo.homestay.model.User;
import demo.homestay.security.UserPrincipal;

public interface UserService {
	ResponseEntity<?> register(User user);
	ResponseEntity<?> login(User user);
	UserPrincipal loadUserByUsername(String userName);
	List<User> getAll();
	ResponseEntity<?> findById(Integer userId);
	ResponseEntity<?> updatePassWord(Integer userId, String newPassWord);
	ResponseEntity<?> delete(Integer userId);
}
