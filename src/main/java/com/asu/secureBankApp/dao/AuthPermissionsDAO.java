package com.asu.secureBankApp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "auth_permission")
public class AuthPermissionsDAO {

	@Id
	@Column(name = "auth_permission_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "perm_name")
	private String permName;
	
	@Column(name = "perm_desc")
	private String permDesc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public String getPermDesc() {
		return permDesc;
	}

	public void setPermDesc(String permDesc) {
		this.permDesc = permDesc;
	}	
}
