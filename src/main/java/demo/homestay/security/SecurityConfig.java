package demo.homestay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//kích hoạt chú thích @PreAuthorize. Điều này có thể được thêm vào bất kỳ lớp nào với chú thích @Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	//Phương thức config (final HttpSecurity http) ghi đè cấu hình HttpBuilder mặc định. 
	//Bởi vì nó trống, nó rời khỏi ứng dụng mà không cần ủy quyền hoặc xác thực.
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		  http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
          .csrf().disable();
//		  http.logout()
//		  .logoutSuccessUrl("/login")
//		  .logoutUrl("/logout")
//		  .permitAll();
	}
}