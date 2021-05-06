package com.ovopark.service.Impl;

import com.ovopark.mapper.UsersMapper;
import com.ovopark.model.login.Users;
import com.ovopark.service.UserRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userRemoteServiceImpl")
public class UserRemoteServiceImpl implements UserRemoteService {
	private static final Logger logger = LoggerFactory.getLogger(UserRemoteServiceImpl.class);

	@Autowired
	private UsersMapper usersMapper;

	@Override
	public Users queryUserById(Integer userId) {
		return usersMapper.selectByPrimaryKey(userId);
	}

}
