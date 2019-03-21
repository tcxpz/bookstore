package com.baidu.bookstore.order.domain;

import java.util.Date;
import java.util.List;

import com.baidu.bookstore.user.domain.User;

public class Order {
	private String oid;
	private Date ordertime;
	private double total;
	private int state;	//	1表示未付款 2表示待发货  3表示等待确认收货 4表示交易完成
	private User user;
	private String address;
	
	private List<OrderItem> orderItemList;//当前订单下所有条目
	
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", price="
				+ total + ", state=" + state + ", user=" + user + ", address="
				+ address + "]";
	}
}
