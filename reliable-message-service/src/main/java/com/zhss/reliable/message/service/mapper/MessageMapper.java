package com.zhss.reliable.message.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhss.reliable.message.service.domain.Message;

@Mapper
public interface MessageMapper {

	@Insert("INSERT INTO message("
				+ "content,"
				+ "status,"
				+ "created_time"
			+ ") VALUES("
				+ "#{content},"
				+ "#{status},"
				+ "#{createdTime}"
			+ ")") 
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")  
	public Integer create(Message message);  
	
	@Update("UPDATE message SET "
				+ "status=#{status},"
				+ "removed_time=#{removedTime} "
			+ "WHERE id=#{id}")  
	public void remove(Message message);   
	
	@Update("UPDATE message SET "
			+ "status=#{status},"
			+ "confirmed_time=#{confirmedTime} "
		+ "WHERE id=#{id}")  
	public void confirm(Message message); 
	
	@Select("SELECT * FROM message WHERE id=#{id}")  
	@Results({
		@Result(id = true, column = "id", property = "id"),
		@Result(column = "content", property = "content"),
		@Result(column = "status", property = "status"),
		@Result(column = "created_time", property = "createdTime"),
		@Result(column = "removed_time", property = "removedTime"),
		@Result(column = "confirmed_time", property = "confirmedTime"),
		@Result(column = "finished_time", property = "finishedTime") 
	})
	public Message findById(@Param("id") Long id);
	
	@Select("SELECT * FROM message WHERE status=#{status}")     
	@Results({
		@Result(id = true, column = "id", property = "id"),
		@Result(column = "content", property = "content"),
		@Result(column = "status", property = "status"),
		@Result(column = "created_time", property = "createdTime"),
		@Result(column = "removed_time", property = "removedTime"),
		@Result(column = "confirmed_time", property = "confirmedTime"),
		@Result(column = "finished_time", property = "finishedTime") 
	})
	public List<Message> findByStatus(@Param("status") Integer status); 
	
	@Update("UPDATE message SET "
			+ "status=#{status},"
			+ "finished_time=#{finishedTime} "
		+ "WHERE id=#{id}")  
	public void finish(Message message); 
	
	@Update("UPDATE message SET content=#{content} WHERE id=#{id}")  
	public void updateContent(Message message);
	
}
