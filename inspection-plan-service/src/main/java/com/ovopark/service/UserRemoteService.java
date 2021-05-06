package com.ovopark.service;



import com.ovopark.model.login.Users;

public interface UserRemoteService {


	public Users queryUserById(Integer userId);
}