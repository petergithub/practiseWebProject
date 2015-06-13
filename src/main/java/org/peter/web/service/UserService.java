package org.peter.web.service;

import java.util.List;

import org.peter.web.domain.User;
import org.springframework.stereotype.Service;

@Service("userService")
public interface UserService {
	public User getUserById(int userId);

	public void updateUser(User user);

	public void insertUser(User user);

	public void insertUserTestTransaction(List<User> users);

	public int getCount();

	public void insertUsers(List<User> users);
}
