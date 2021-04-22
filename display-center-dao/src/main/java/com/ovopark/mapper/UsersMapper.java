package com.ovopark.mapper;

import com.ovopark.model.login.Users;

/**
 * @author user
 */


public interface UsersMapper {

    Users selectByPrimaryKey(Integer id);

}