package demo.homestay.service;

import org.springframework.http.ResponseEntity;

import demo.homestay.model.UserInfo;

public interface UserInfoService {
	ResponseEntity<?> findById(Integer userId);
	ResponseEntity<?> save(int userId, UserInfo userInfo);
	ResponseEntity<?> update(int userId,UserInfo userInfo);
	
}
