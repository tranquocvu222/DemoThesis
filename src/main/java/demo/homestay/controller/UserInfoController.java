package demo.homestay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.homestay.model.UserInfo;
import demo.homestay.service.UserInfoService;

@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
	@Autowired
	private	UserInfoService userInfoService;
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority('user','admin')")
	public ResponseEntity<?> findByUserId(@PathVariable("userId") int userId){
		return userInfoService.findById(userId);
	}
	
	@PostMapping("/{userId}")
	@PreAuthorize("hasAnythority('user', 'admin')")
	public ResponseEntity<?> createUserInfo(@PathVariable("userId") int userId, @RequestBody UserInfo userInfo){
		return userInfoService.save(userId, userInfo);
	}
	
	@PutMapping("/{userId}") 
	@PreAuthorize("hasAnythority('user', 'admin')")
	public ResponseEntity<?> updateUserInfo(@PathVariable("userId") int userId, @RequestBody UserInfo userInfo){
		return userInfoService.update(userId, userInfo);
	}
}
