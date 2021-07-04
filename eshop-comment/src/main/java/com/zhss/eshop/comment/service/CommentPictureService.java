package com.zhss.eshop.comment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.zhss.eshop.comment.domain.CommentPictureDTO;

/**
 * 评论晒图管理模块的service组件接口
 * @author zhonghuashishan
 *
 */
public interface CommentPictureService {

	/**
	 * 保存评论晒图
	 * @param appBasePath 当前应用根路径
	 * @param commentInfoId 评论信息id
	 * @param files 评论晒图
	 * @return 处理结果
	 * @throws Exception
	 */
	void saveCommentPictures(String appBasePath, 
			Long commentInfoId, MultipartFile[] files) throws Exception;
	
	/**
	 * 根据评论信息id查询图片
	 * @param commentId 评论信息id
	 * @return 评论图片
	 * @throws Exception
	 */
	List<CommentPictureDTO> listByCommentId(Long commentId) throws Exception;
	
	/**
	 * 根据id查询图片
	 * @param id 评论图片id
	 * @return 评论图片
	 * @throws Exception
	 */
	CommentPictureDTO getById(Long id) throws Exception;
	
}
