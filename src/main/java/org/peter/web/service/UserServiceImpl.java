package org.peter.web.service;

import java.util.List;

import org.peter.web.domain.User;
import org.peter.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(int userId) {
		return this.userMapper.getById(userId);
	}

	public void updateUser(User user) {
		userMapper.update(user);
	}

	public void insertUser(User user) {
		userMapper.insert(user);
	}
	
	@Transactional
	@Override
	public void insertUsers(List<User> users) {
		for (int i = 0; i < users.size(); i++) {
			if (i < 2) {
				this.userMapper.insert(users.get(i));
			} else {
				throw new RuntimeException();
			}
		}
	}

	/**
	 * 事务处理必须抛出异常，Spring才会帮助事务回滚
	 * @param users
	 */
	@Transactional
	@Override
	public void insertUserTestTransaction(List<User> users) {
		for (int i = 0; i < users.size(); i++) {
			if (i < 2) {
				this.userMapper.insert(users.get(i));
			} else {
				throw new RuntimeException();
			}
		}
	}
	
	public int getCount() {
		return userMapper.getCount();
	}
}
