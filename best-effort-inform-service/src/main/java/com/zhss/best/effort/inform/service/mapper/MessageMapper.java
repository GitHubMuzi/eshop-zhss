package com.zhss.best.effort.inform.service.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhss.best.effort.inform.service.domain.Message;

@Mapper
public interface MessageMapper {

	@Insert("INSERT INTO message("
				+ "phone_number,"
				+ "content,"
				+ "retry_rule,"
				+ "status,"
				+ "latest_send_time,"
				+ "current_retry_count"
			+ ") VALUES("
				+ "#{phoneNumber},"
				+ "#{content},"
				+ "#{retryRule},"
				+ "#{status},"
				+ "#{latestSendTime},"
				+ "#{currentRetryCount}"
			+ ")") 
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id") 
	public void create(Message message);
	
	@Select("SELECT * FROM message WHERE status=#{status}")  
	@Results({
		@Result(id = true, column = "id", property = "id"),
		@Result(column = "phone_number", property = "phoneNumber"), 
		@Result(column = "content", property = "content"),
		@Result(column = "retry_rule", property = "retryRule"), 
		@Result(column = "status", property = "status"), 
		@Result(column = "latest_send_time", property = "latestSendTime"), 
		@Result(column = "current_retry_count", property = "currentRetryCount")  
	})
	public List<Message> findByStatus(@Param("status") Integer status);
	
	@Update("UPDATE message SET status=#{status} WHERE id=#{id}") 
	public void updateStatus(
			@Param("id") Long id, 
			@Param("status") Integer status);
	
	@Update("UPDATE message SET current_retry_count=current_retry_count+1 WHERE id=#{id}") 
	public void increaseCurrentRetryCount(@Param("id") Long id);
	
	@Update("UPDATE message SET latest_send_time=#{latestSendTime} WHERE id=#{id}") 
	public void updateLatestSendTime(
			@Param("id") Long id, 
			@Param("latestSendTime") Date latestSendTime);
	
}
