package sample.service;

import sample.common.dao.entity.Login;

public interface LoginService {
	Login findByUsername(String username);
	void register(String username, String password);
	boolean login(String username, String password);
}
