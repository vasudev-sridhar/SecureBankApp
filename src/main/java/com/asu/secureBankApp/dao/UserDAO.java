package com.asu.secureBankApp.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "user")
public class UserDAO {
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String username;

	@NotNull
	private String password;
	
	@NotNull
	private String name;
	
	private Date dob;
	
	@NotNull
	private String contact;
	
	@NotNull
	@Column(name = "email_id")
	private String emailId;
	
	private String address;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "auth_role_id", nullable = false, foreignKey = @ForeignKey(name="FK_user_role"))
	@ManyToOne(fetch = FetchType.LAZY)
	private AuthRoleDAO authRole;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable=false, insertable = false, updatable = false)
	private List<AccountDAO> accounts;


	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AuthRoleDAO getAuthRole() {
		return authRole;
	}

	public void setAuthRole(AuthRoleDAO authRole) {
		this.authRole = authRole;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<AccountDAO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDAO> accounts) {
		this.accounts = accounts;
	}
	
}
