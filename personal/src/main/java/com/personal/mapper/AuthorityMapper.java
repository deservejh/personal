package com.personal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.personal.domain.Authority;

@Repository
@Mapper
public interface AuthorityMapper {
	
	List<Authority> selectAuthority(int userId);

}
