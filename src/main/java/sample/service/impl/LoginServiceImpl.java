package sample.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.common.dao.entity.Login;
import sample.common.dao.mapper.LoginMapper;
import sample.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginServiceImpl(LoginMapper loginMapper, PasswordEncoder passwordEncoder) {
        this.loginMapper = loginMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Login findByUsername(String username) {
        return loginMapper.findByUsername(username);
    }

    @Override
    @Transactional
    public void register(String username, String password) {
        if (loginMapper.findByUsername(username) != null) {
            throw new IllegalArgumentException("ユーザー名は既に使われています");
        }
        Login login = new Login();
        login.setUsername(username);
        login.setPassword(passwordEncoder.encode(password));
        loginMapper.insert(login);
    }

    @Override
    public boolean login(String username, String password) {
        Login login = loginMapper.findByUsername(username);
        if (login == null) {
            return false;
        }
        return passwordEncoder.matches(password, login.getPassword());
    }
}