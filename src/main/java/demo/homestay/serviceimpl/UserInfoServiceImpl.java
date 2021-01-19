package demo.homestay.serviceimpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import demo.homestay.model.UserInfo;
import demo.homestay.repository.UserInfoRepository;
import demo.homestay.repository.UserRepository;
import demo.homestay.security.SecurityAuditorAware;
import demo.homestay.service.UserInfoService;


@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private SecurityAuditorAware securityAuditorAware;
	
	@Override
	public ResponseEntity<?> findById(Integer userId) {
		Integer currentUserId = securityAuditorAware.getCurrentAuditor().get();
		if(userId != currentUserId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission");
		}
		if (Integer.toString(userId) == null || Integer.toString(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter UserId");
		}
		if (!userInfoRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found User");
		}
		UserInfo userInfo = userInfoRepository.findById(userId).orElse(null);
		return ResponseEntity.ok(userInfo);
	}

	@Override
	public ResponseEntity<?> save(int userId, UserInfo userInfo) {
		// TODO Auto-generated method stub
		Integer currentUserId = securityAuditorAware.getCurrentAuditor().get();
		if(userId != currentUserId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission");
		}
		if (Integer.toString(userId) == null || Integer.toString(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter UserId");
		}
		if (!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found User");
		}
		if (null != userInfoRepository.findById(userId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserInfo already exist");
		}
		UserInfo userCreate  = new UserInfo();
		userCreate.setId(userInfo.getId());
		userCreate.setFullname(userInfo.getFullname());
		userCreate.setEmail(userInfo.getEmail());
		userCreate.setAddress(userInfo.getAddress());
		userCreate.setPhone(userInfo.getPhone());
		userCreate.setUser(userRepository.findById(userId).get());
		userInfoRepository.saveAndFlush(userCreate);
		return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
	}

	@Override
	public ResponseEntity<?> update(int userId, UserInfo userInfo) {
		// TODO Auto-generated method stub
		Integer currentUserId = securityAuditorAware.getCurrentAuditor().get();
		if(userId != currentUserId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission");
		}
		if (Integer.toString(userId) == null || Integer.toString(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter UserId");
		}
		if (!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found User");
		}
		if (null == userInfoRepository.findById(userId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng chưa có thông tin");
		}
		UserInfo userUpdate  = userInfoRepository.findById(userId);
		userUpdate.setFullname(userInfo.getFullname());
		userUpdate.setEmail(userInfo.getEmail());
		userUpdate.setAddress(userInfo.getAddress());
		userUpdate.setPhone(userInfo.getPhone());
		userInfoRepository.saveAndFlush(userUpdate);
		return ResponseEntity.ok(userUpdate);
	}

}
