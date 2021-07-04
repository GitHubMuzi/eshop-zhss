package com.zhss.eshop.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.auth.domain.PriorityDTO;
import com.zhss.eshop.auth.domain.PriorityVO;
import com.zhss.eshop.auth.service.PriorityService;
import com.zhss.eshop.auth.service.impl.Priority;
import com.zhss.eshop.common.util.DateProvider;

/**
 * 权限管理模块的controller组件
 * @author zhonghuashishan
 *
 */
@RestController
@RequestMapping("/auth/priority")  
public class PriorityController {
	
	private static final Logger logger = LoggerFactory.getLogger(PriorityController.class);

	/**
	 * 权限管理模块的service组件
	 */
	@Autowired
	private PriorityService priorityService;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 查询根权限
	 * @return 根权限集合
	 */
	@GetMapping("/root")
	public List<PriorityVO> listRootPriorities() {
		try {
			List<PriorityDTO> priorityDTOs = priorityService.listRootPriorities();
			if(priorityDTOs == null) {
				priorityDTOs = new ArrayList<PriorityDTO>();
			}
			
			List<PriorityVO> priorityVOs = new ArrayList<PriorityVO>(priorityDTOs.size());
			for(PriorityDTO priorityDTO : priorityDTOs) {
				priorityVOs.add(convertPriorityDTO2VO(priorityDTO));  
			}
			
			return priorityVOs;
		} catch (Exception e) {
			logger.error("error", e); 
		}
		return new ArrayList<PriorityVO>();
	}
	
	/**
	 * 根据父权限id查询子权限
	 * @param parentId 父权限id
	 * @return 子权限
	 */
	@GetMapping("/child/{parentId}") 
	public List<PriorityVO> listChildPriorities(
			@PathVariable("parentId") Long parentId) {
		try {
			List<PriorityDTO> priorityDTOs = priorityService.listChildPriorities(parentId);
			if(priorityDTOs == null) {
				priorityDTOs = new ArrayList<PriorityDTO>();
			}
			
			List<PriorityVO> priorityVOs = new ArrayList<PriorityVO>(priorityDTOs.size());
			for(PriorityDTO priorityDTO : priorityDTOs) {
				priorityVOs.add(convertPriorityDTO2VO(priorityDTO));  
			}
			
			return priorityVOs;
		} catch (Exception e) {
			logger.error("error", e); 
		}
		return new ArrayList<PriorityVO>();
	}
	
	/**
	 * 根据id查询权限
	 * @param id 权限id
	 * @return 权限
	 */
	@GetMapping("/{id}")
	public PriorityVO getPriorityById(@PathVariable("id") Long id) {
		try {
			PriorityDTO priorityDTO = priorityService.getPriorityById(id);
			if(priorityDTO == null) {
				priorityDTO = new PriorityDTO();
			}
			
			return convertPriorityDTO2VO(priorityDTO);
		} catch (Exception e) {
			logger.error("error", e); 
		}
		return new PriorityVO();
	}
	
	/**
	 * 查询账号被授权的权限树
	 * @param accountId 账号id
	 * @return 权限树
	 */
	@GetMapping("/authorized/tree/{accountId}")  
	public List<Priority> listAuthorizedTree(
			@PathVariable("accountId") Long accountId) {
		try {
			return priorityService.listAuthorizedByAccountId(accountId);
		} catch (Exception e) {
			logger.error("error", e); 
			return new ArrayList<Priority>();
		}
	}
	
	/**
	 * 判断账号对指定编号的权限是否有授权记录
	 * @param accountId 账号id
	 */
	@GetMapping("/authorized/code/{accountId}")  
	public Boolean existAuthorizedByCode(
			@PathVariable("accountId") Long accountId,
			String code) {
		try {
			return priorityService.existAuthorizedByCode(accountId, code);
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 判断账号对指定url的权限是否有授权记录
	 * @param accountId 账号id
	 */
	@GetMapping("/authorized/url/{accountId}")  
	public Boolean existAuthorizedByUrl(
			@PathVariable("accountId") Long accountId,
			String url) {
		try {
			return priorityService.existAuthorizedByUrl(accountId, url);
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 新增权限
	 * @param priorityDO 权限DO对象
	 */
	@PostMapping("/") 
	public Boolean savePriority(@RequestBody PriorityVO priorityVO) {
		try {
			priorityService.savePriority(convertPriorityVO2DTO(priorityVO)); 
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 更新权限
	 * @param priorityDO 权限DO对象
	 */
	@PutMapping("/{id}")  
	public Boolean updatePriority(@RequestBody PriorityVO priorityVO) {
		try {
			priorityService.updatePriority(convertPriorityVO2DTO(priorityVO)); 
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 删除权限
	 */
	@DeleteMapping("/{id}")
	public Boolean removePriority(@PathVariable("id") Long id) {
		try {
			return priorityService.removePriority(id);
		} catch (Exception e) {
			logger.error("error", e);
		}
		return false;
	}
	
	/**
	 * 将权限DTO对象转换为VO对象
	 * @param priorityDTO
	 * @return
	 * @throws Exception
	 */
	private PriorityVO convertPriorityDTO2VO(PriorityDTO priorityDTO) throws Exception {
		PriorityVO priorityVO = priorityDTO.clone(PriorityVO.class);
		priorityVO.setGmtCreate(dateProvider.formatDatetime(priorityDTO.getGmtCreate()));  
		priorityVO.setGmtModified(dateProvider.formatDatetime(priorityDTO.getGmtModified())); 
		return priorityVO;
	}
	
	/**
	 * 将权限VO对象转换为DTO对象
	 * @param priorityVO
	 * @return
	 * @throws Exception
	 */
	private PriorityDTO convertPriorityVO2DTO(PriorityVO priorityVO) throws Exception {
		PriorityDTO priorityDTO = priorityVO.clone(PriorityDTO.class);
		if(priorityVO.getGmtCreate() != null) {
			priorityDTO.setGmtCreate(dateProvider.parseDatetime(priorityVO.getGmtCreate())); 
		}
		if(priorityVO.getGmtModified() != null) {  
			priorityDTO.setGmtModified(dateProvider.parseDatetime(priorityVO.getGmtModified())); 
		}
		return priorityDTO;
	}
	
}
