package demo.homestay.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name ="userinfo")
public class UserInfo{
	
	@Id
	@Column(name = "user_id")
	private Integer Id;
	private String fullname;
	private String address;
	private String email;
	private String phone;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public UserInfo(String fullname, String address, String email, String phone) {
		super();
		this.fullname = fullname;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
