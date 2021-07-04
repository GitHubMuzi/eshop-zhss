package com.zhss.eshop.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhss.eshop.order.domain.OrderInfoDO;
import com.zhss.eshop.order.domain.OrderInfoQuery;

/**
 * 订单管理mapper组件
 * @author zhonghuashishan
 *
 */
@Mapper
public interface OrderInfoMapper {

	/**
	 * 新增订单
	 * @param order
	 */
	@Insert("INSERT INTO order_info("
				+ "user_account_id,"
				+ "username,"
				+ "order_no,"
				+ "order_status,"
				+ "consignee,"
				+ "delivery_address,"
				+ "consignee_cell_phone_number,"
				+ "freight,"
				+ "pay_type,"
				+ "total_amount,"
				+ "discount_amount,"
				+ "coupon_amount,"
				+ "payable_amount,"
				+ "invoice_title,"
				+ "taxpayer_id,"
				+ "order_comment,"
				+ "is_published_comment,"
				+ "confirm_receipt_time,"
				+ "gmt_create,"
				+ "gmt_modified"
			+ ") VALUES("
				+ "#{userAccountId},"
				+ "#{username},"
				+ "#{orderNo},"
				+ "#{orderStatus},"
				+ "#{consignee},"
				+ "#{deliveryAddress},"
				+ "#{consigneeCellPhoneNumber},"
				+ "#{freight},"
				+ "#{payType},"
				+ "#{totalAmount},"
				+ "#{discountAmount},"
				+ "#{couponAmount},"
				+ "#{payableAmount},"
				+ "#{invoiceTitle},"
				+ "#{taxpayerId},"
				+ "#{orderComment},"
				+ "#{publishedComment},"
				+ "#{confirmReceiptTime},"
				+ "#{gmtCreate},"
				+ "#{gmtModified}"
			+ ")") 
	@Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
	void save(OrderInfoDO order);
	
	/**
	 * 分页查询订单
	 * @param query 查询条件
	 * @return 订单
	 */
	@Select("SELECT "
				+ "a.id,"
				+ "a.gmt_create,"
				+ "a.order_no,"
				+ "a.consignee,"
				+ "a.total_amount,"
				+ "a.discount_amount,"
				+ "a.coupon_amount,"
				+ "a.freight,"
				+ "a.payable_amount,"
				+ "a.pay_type,"
				+ "a.order_status,"
				+ "a.user_account_id,"
				+ "a.username,"
				+ "a.confirm_receipt_time "
			+ "FROM order_info a,"
			+ "("
				+ "SELECT id "
				+ "FROM order_info "
				+ "WHERE user_account_id=#{userAccountId} "
				+ "LIMIT #{offset},#{size} "  
			+ ") b "
			+ "WHERE a.id=b.id"
	)
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "gmt_create", property = "gmtCreate"),
		@Result(column = "order_no", property = "orderNo"),
		@Result(column = "consignee", property = "consignee"),
		@Result(column = "total_amount", property = "totalAmount"),
		@Result(column = "discount_amount", property = "discountAmount"),
		@Result(column = "coupon_amount", property = "couponAmount"),
		@Result(column = "freight", property = "freight"),
		@Result(column = "payable_amount", property = "payableAmount"),
		@Result(column = "pay_type", property = "payType"),
		@Result(column = "order_status", property = "orderStatus"),
		@Result(column = "user_account_id", property = "userAccountId"),
		@Result(column = "username", property = "username"),
		@Result(column = "confirm_receipt_time", property = "confirmReceiptTime")
	})
	List<OrderInfoDO> listByPage(OrderInfoQuery query);
	
	/**
	 * 根据id查询订单
	 * @param id 订单id
	 * @return 订单
	 */
	@Select("SELECT "
				+ "id,"
				+ "order_no,"
				+ "consignee,"
				+ "delivery_address,"
				+ "consignee_cell_phone_number,"
				+ "total_amount,"
				+ "discount_amount,"
				+ "coupon_amount,"
				+ "freight,"
				+ "payable_amount,"
				+ "pay_type,"
				+ "invoice_title,"
				+ "taxpayer_id,"
				+ "order_status,"
				+ "user_account_id,"
				+ "username,"
				+ "order_comment,"
				+ "is_published_comment,"
				+ "confirm_receipt_time," 
				+ "gmt_create,"
				+ "gmt_modified "
			+ "FROM order_info "
			+ "WHERE id=#{id}"
	)
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "order_no", property = "orderNo"),
		@Result(column = "consignee", property = "consignee"),
		@Result(column = "total_amount", property = "totalAmount"),
		@Result(column = "discount_amount", property = "discountAmount"),
		@Result(column = "coupon_amount", property = "couponAmount"),
		@Result(column = "freight", property = "freight"),
		@Result(column = "payable_amount", property = "payableAmount"),
		@Result(column = "pay_type", property = "payType"),
		@Result(column = "order_status", property = "orderStatus"),
		@Result(column = "delivery_address", property = "deliveryAddress"),
		@Result(column = "consignee_cell_phone_number", property = "consigneeCellPhoneNumber"),
		@Result(column = "invoice_title", property = "invoiceTitle"),
		@Result(column = "taxpayer_id", property = "taxpayerId"),
		@Result(column = "user_account_id", property = "userAccountId"),
		@Result(column = "username", property = "username"),
		@Result(column = "order_comment", property = "orderComment"),
		@Result(column = "is_published_comment", property = "publishedComment"),
		@Result(column = "confirm_receipt_time", property = "confirmReceiptTime"),
		@Result(column = "gmt_create", property = "gmtCreate"),
		@Result(column = "gmt_modified", property = "gmtModified")
 	})
	OrderInfoDO getById(@Param("id") Long id);
	
	/**
	 * 更新订单状态
	 * @param order 订单
	 */
	@Update("UPDATE order_info SET "
				+ "order_status=#{orderStatus},"
				+ "confirm_receipt_time=#{confirmReceiptTime},"
				+ "is_published_comment=#{publishedComment},"
				+ "gmt_modified=#{gmtModified} "
			+ "WHERE id=#{id}") 
	void update(OrderInfoDO order);
	
	/**
	 * 根据状态查询订单
	 * @param orderStatus 订单状态
	 * @return 订单
	 */
	@Select("SELECT "
				+ "id,"
				+ "order_no,"
				+ "consignee,"
				+ "delivery_address,"
				+ "consignee_cell_phone_number,"
				+ "total_amount,"
				+ "discount_amount,"
				+ "coupon_amount,"
				+ "freight,"
				+ "payable_amount,"
				+ "pay_type,"
				+ "invoice_title,"
				+ "taxpayer_id,"
				+ "order_status,"
				+ "user_account_id,"
				+ "username,"
				+ "order_comment,"
				+ "is_published_comment,"
				+ "confirm_receipt_time," 
				+ "gmt_create,"
				+ "gmt_modified " 
			+ "FROM order_info "
			+ "WHERE order_status=#{orderStatus}" 
	)
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "order_no", property = "orderNo"),
		@Result(column = "consignee", property = "consignee"),
		@Result(column = "total_amount", property = "totalAmount"),
		@Result(column = "discount_amount", property = "discountAmount"),
		@Result(column = "coupon_amount", property = "couponAmount"),
		@Result(column = "freight", property = "freight"),
		@Result(column = "payable_amount", property = "payableAmount"),
		@Result(column = "pay_type", property = "payType"),
		@Result(column = "order_status", property = "orderStatus"),
		@Result(column = "delivery_address", property = "deliveryAddress"),
		@Result(column = "consignee_cell_phone_number", property = "consigneeCellPhoneNumber"),
		@Result(column = "invoice_title", property = "invoiceTitle"),
		@Result(column = "taxpayer_id", property = "taxpayerId"),
		@Result(column = "user_account_id", property = "userAccountId"),
		@Result(column = "username", property = "username"),
		@Result(column = "order_comment", property = "orderComment"),
		@Result(column = "is_published_comment", property = "publishedComment"),
		@Result(column = "confirm_receipt_time", property = "confirmReceiptTime"),
		@Result(column = "gmt_create", property = "gmtCreate"),
		@Result(column = "gmt_modified", property = "gmtModified")
 	})
	List<OrderInfoDO> listByStatus(@Param("orderStatus") Integer orderStatus); 
	
	/**
	 * 查询确认收货时间超过了7天而且还没有发表评论的订单
	 * @return 订单
	 */
	@Select("SELECT "
				+ "id,"
				+ "order_no,"
				+ "consignee,"
				+ "delivery_address,"
				+ "consignee_cell_phone_number,"
				+ "total_amount,"
				+ "discount_amount,"
				+ "coupon_amount,"
				+ "freight,"
				+ "payable_amount,"
				+ "pay_type,"
				+ "invoice_title,"
				+ "taxpayer_id,"
				+ "order_status,"
				+ "user_account_id,"
				+ "username,"
				+ "order_comment,"
				+ "is_published_comment,"
				+ "confirm_receipt_time," 
				+ "gmt_create,"
				+ "gmt_modified " 
			+ "FROM order_info "
			+ "WHERE DATEDIFF(NOW(),IFNULL(confirm_receipt_time,NOW())) > 7 "
			+ "AND is_published_comment=0" 
	)
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "order_no", property = "orderNo"),
		@Result(column = "consignee", property = "consignee"),
		@Result(column = "total_amount", property = "totalAmount"),
		@Result(column = "discount_amount", property = "discountAmount"),
		@Result(column = "coupon_amount", property = "couponAmount"),
		@Result(column = "freight", property = "freight"),
		@Result(column = "payable_amount", property = "payableAmount"),
		@Result(column = "pay_type", property = "payType"),
		@Result(column = "order_status", property = "orderStatus"),
		@Result(column = "delivery_address", property = "deliveryAddress"),
		@Result(column = "consignee_cell_phone_number", property = "consigneeCellPhoneNumber"),
		@Result(column = "invoice_title", property = "invoiceTitle"),
		@Result(column = "taxpayer_id", property = "taxpayerId"),
		@Result(column = "user_account_id", property = "userAccountId"),
		@Result(column = "username", property = "username"),
		@Result(column = "order_comment", property = "orderComment"),
		@Result(column = "is_published_comment", property = "publishedComment"),
		@Result(column = "confirm_receipt_time", property = "confirmReceiptTime"),
		@Result(column = "gmt_create", property = "gmtCreate"),
		@Result(column = "gmt_modified", property = "gmtModified")
 	})
	List<OrderInfoDO> listNotPublishedCommentOrders(); 
	
}
