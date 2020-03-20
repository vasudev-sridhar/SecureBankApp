package com.asu.secureBankApp.dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import constants.RoleType;

@Entity(name = "auth_role")
public class AuthRoleDAO {

	@Id
	@Column(name = "auth_role_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToMany(mappedBy = "authRole", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<AuthRolePermissionDAO> permissions;

	@NotNull
	@Column(name = "role_name")
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Column(name = "role_desc")
	private String roleDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<AuthRolePermissionDAO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<AuthRolePermissionDAO> permissions) {
		this.permissions = permissions;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
