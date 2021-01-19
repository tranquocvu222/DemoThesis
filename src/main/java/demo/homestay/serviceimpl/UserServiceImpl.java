package demo.homestay.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import demo.homestay.model.Token;
import demo.homestay.model.Role;
import demo.homestay.model.User;
import demo.homestay.repository.RoleRepository;
import demo.homestay.repository.UserInfoRepository;
import demo.homestay.repository.UserRepository;
import demo.homestay.service.TokenService;
import demo.homestay.service.UserService;
import demo.homestay.security.*;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private SecurityAuditorAware securityAuditorAware;
	
	//để lấy ra thông tin của tài khoản có username là username được nhập từ màn hình login
	@Override
	public UserPrincipal loadUserByUsername(String userName) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserName(userName);
		UserPrincipal userPrincipal = new UserPrincipal();
		if(null != user) {
			Set<String> authorities = new HashSet<>();
			if(null != user.getRole()) {
				authorities.add(user.getRole().getRoleName());
			}
			userPrincipal.setUserId(user.getId());
			userPrincipal.setPassword(user.getPassWord());
			userPrincipal.setUsername(user.getUserName());
			userPrincipal.setAuthorities(authorities);
		}
		return userPrincipal;
	}
	@Override
	public ResponseEntity<?> login(User user) {
		// TODO Auto-generated method stub
		if(user.getUserName() == null || user.getUserName().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter your username");
		}
		if(user.getPassWord() == null || user.getUserName().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter your password");
		}
		UserPrincipal userPrincipal = loadUserByUsername(user.getUserName());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(null == user || !encoder.matches(user.getPassWord(), userPrincipal.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or Password wrong");
		}
		Token token = new Token();
		token.setToken(jwtUtil.generateToken(userPrincipal));
		token.setTokenExpDate(jwtUtil.generateExpirationDate());
		tokenService.save(token);
		return ResponseEntity.ok(token.getToken());
	}

	@Override
	public ResponseEntity<?> register(User user) {
		if(user.getUserName() == null || user.getUserName().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter your username");
		}
		if(user.getPassWord() == null || user.getPassWord().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter your password");
		}
		if(null !=userRepository.findByUserName(user.getUserName())) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Username already exists");
		}
		User userNew  = new User();
		userNew.setUserName(user.getUserName());
		userNew.setPassWord(new BCryptPasswordEncoder().encode(user.getPassWord()));
		Role role = roleRepository.findRoleByRoleName("user");
		userNew.setRole(role);
		userRepository.saveAndFlush(userNew);
		return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
	}

//	@Override
//	public User findUserByUserName(String userName) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public ResponseEntity<?> findById(Integer userId) {
		if (Integer.toString(userId) == null || Integer.toString(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter UserId");
		}
		if (!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found User");
		}
		User user = userRepository.findById(userId).orElse(null);
		return ResponseEntity.ok(user);
	}

	@Override
	public ResponseEntity<?> updatePassWord(Integer userId, String newPassword) {
		// TODO Auto-generated method stub
		if (Integer.toString(userId) == null || Integer.toString(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
		if(!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found User");
		}
		User user = userRepository.findById(userId).orElse(null);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(user.getPassWord(), newPassword)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The new password is the same as the old one");
		}
		user.setPassWord(new BCryptPasswordEncoder().encode(newPassword));
		userRepository.saveAndFlush(user);
		return ResponseEntity.ok(user);
	}

	@Override
	public ResponseEntity<?> delete(Integer userId) {
		// TODO Auto-generated method stub
		if (Integer.toString(userId) == null || Integer.toString(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter UserId");
		}
		if(!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found User");
		}
		if(!userInfoRepository.findById(userId).isPresent()) {
			userRepository.deleteById(userId);
			return ResponseEntity.ok("Delete success");
		}
		userInfoRepository.deleteById(userId);
		userRepository.deleteById(userId);
		return ResponseEntity.ok("Delete success");
	}

}
