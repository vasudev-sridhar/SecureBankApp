package com.asu.secureBankApp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "auth_role_permission")
public class AuthRolePermissionDAO {

	@Id
	@Column(name = "auth_role_permission_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "auth_role_id", nullable = false, foreignKey = @ForeignKey(name="FK_auth_role_permission__auth_role"))
	private AuthRoleDAO authRole;
	
	@ManyToOne
	@JoinColumn(name = "auth_permission_id", nullable = false, foreignKey = @ForeignKey(name="FK_auth_role_permission__auth_permission"))
	private AuthPermissionsDAO authPermission;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AuthRoleDAO getAuthRole() {
		return authRole;
	}

	public void setAuthRole(AuthRoleDAO authRole) {
		this.authRole = authRole;
	}

	public AuthPermissionsDAO getAuthPermission() {
		return authPermission;
	}

	public void setAuthPermissionId(AuthPermissionsDAO authPermissionId) {
		this.authPermission = authPermissionId;
	}

}
