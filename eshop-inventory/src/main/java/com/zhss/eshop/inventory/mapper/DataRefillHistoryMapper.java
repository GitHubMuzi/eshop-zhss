package com.zhss.eshop.inventory.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataRefillHistoryMapper {

	@Insert("INSERT INTO data_refill_history(data_refill_no) VALUES(#{dataRefillNo})")  
	public void create(@Param("dataRefillNo") String dataRefillNo);
	
	@Delete("DELETE FROM data_refill_history WHERE data_refill_no=#{dataRefillNo}") 
	public void remove(@Param("dataRefillNo") String dataRefillNo);
	
}
