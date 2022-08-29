package com.copin.asset.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AccountMapper {
	
	@Select("select pw from account where id = #{id}")
	public String getidCheck(String id);
	
	@Select("select * from account where id = #{param1} and pw  = #{param2}")
	public Map<String,Object> getAccount(String id, String pw);
	
}
