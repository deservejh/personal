package com.personal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.personal.domain.User;

@Repository
@Mapper
public interface UserMapper {
	
	User selectUserByUserName(String userName);

}
