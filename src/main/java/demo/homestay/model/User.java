package demo.homestay.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import demo.homestay.model.UserInfo;

@Entity
@Table(name="user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer Id;
	private String userName;
	private String passWord;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name="role_id",nullable = false)
	private Role role;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@PrimaryKeyJoinColumn
	private UserInfo userInfo;
	
	
	

	public Integer getId() {
		return Id;
	}


	public void setId(Integer id) {
		Id = id;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}

	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userName, String passWord) {
		super();
		this.userName = userName;
		this.passWord = passWord;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
