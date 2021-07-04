package com.zhss.eshop.order.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UniqueRecordMapper {

	@Insert("INSERT INTO unique_record(unique_value) VALUES(#{uniqueValue})")  
	void insert(@Param("uniqueValue") String uniqueValue);
	
}
