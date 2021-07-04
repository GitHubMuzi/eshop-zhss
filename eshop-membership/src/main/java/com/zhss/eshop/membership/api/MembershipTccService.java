package com.zhss.eshop.membership.api;

import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableCancel;
import org.bytesoft.compensable.CompensableConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.membership.mapper.UniqueRecordMapper;
import com.zhss.eshop.membership.service.impl.PayOrderMembershipUpdater;

@RestController
@Compensable(interfaceClass = MembershipTccApi.class, simplified = true)
public class MembershipTccService implements MembershipTccApi {

	@Autowired
	private PayOrderMembershipUpdater payOrderMembershipUpdater;
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;
	
	@Override
	@Transactional
	public Boolean informPayOrderEvent(
			@PathVariable("userAccountId")Long userAccountId, 
			@RequestParam("totalOrderAmount") Double totalOrderAmount,
			@RequestParam("orderId") Long orderId) throws Exception {
		uniqueRecordMapper.insert("MembershipTccService_informPayOrderEvent_" + orderId);   
		payOrderMembershipUpdater.execute(userAccountId, totalOrderAmount);
		return true;
	}
	
	@CompensableConfirm
	@Transactional
	public Boolean confirmInformPayOrderEvent(
			@PathVariable("userAccountId")Long userAccountId, 
			@RequestParam("totalOrderAmount") Double totalOrderAmount,
			@RequestParam("orderId") Long orderId) throws Exception {
		return true;
	}
	
	@CompensableCancel
	@Transactional
	public Boolean cancelInformPayOrderEvent(
			@PathVariable("userAccountId")Long userAccountId, 
			@RequestParam("totalOrderAmount") Double totalOrderAmount,
			@RequestParam("orderId") Long orderId) throws Exception {
		payOrderMembershipUpdater.undo(userAccountId, totalOrderAmount);
		return null;
	}

}
