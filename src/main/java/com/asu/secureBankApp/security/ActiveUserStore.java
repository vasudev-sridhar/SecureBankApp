package com.asu.secureBankApp.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ActiveUserStore {

    public List<Integer> users;

    public ActiveUserStore() {
        users = new ArrayList<Integer>();
    }

	public List<Integer> getUsers() {
		return users;
	}

	public void setUsers(List<Integer> users) {
		this.users = users;
	}

}
