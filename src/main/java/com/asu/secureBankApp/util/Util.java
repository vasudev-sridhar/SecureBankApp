package com.asu.secureBankApp.util;

import constants.RoleType;

public class Util {

	public static boolean isEmployee(RoleType role) {
		if(role == RoleType.ADMIN || role == RoleType.TIER1 || role == RoleType.TIER2)
			return true;
		return false;
	}
}
