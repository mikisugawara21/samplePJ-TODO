package sample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;
import sample.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public Login findByUsername(String username) {
		return loginMapper.findByUsername(username);
	}
	
	@Override
	public void register(String username, String password) {
		Login login = new Login();
		login.setUsername(username);
		login.setPassword(password);
		loginMapper.insert(login);
	}
	
	@Override
	public boolean login(String username,String password) {
		Login login = loginMapper.findByUsername(username);
		if (login == null) {
			return false;
		}
		return login.getPassword().equals(password);
	}

}
