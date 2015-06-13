package org.peter.web.mapper;

import java.util.List;

import org.peter.web.domain.User;

public interface UserMapper {

	public void insert(User user);

	public User getById(Integer userId);

	public List<User> getAll();

	public void update(User user);

	public void delete(Integer userId);
	
	public int getCount();

}
