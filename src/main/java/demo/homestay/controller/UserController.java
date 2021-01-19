package demo.homestay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.homestay.model.User;
import demo.homestay.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user){
		return userService.login(user);
	}
	
	
	@GetMapping("/allUsers")
	@PreAuthorize("hasAnyAuthority('admin')")
	public List<User> getAll(){
		return userService.getAll();
	}
	
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<?> findById(@PathVariable("userId") int userId) {
		return userService.findById(userId);
	}
	
	@DeleteMapping("/user/{userId}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId){
		return userService.delete(userId);
	}
	
	@PutMapping("/user/{userId}")
	@PreAuthorize("hasAnyAuthority('user','admin')")
	public ResponseEntity<?> updatePasword(@PathVariable("userId") int userId, @RequestBody String newPassword){
		return userService.updatePassWord(userId, newPassword);
	}
}
