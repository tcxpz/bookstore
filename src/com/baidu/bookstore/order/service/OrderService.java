package com.baidu.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.itcast.jdbc.JdbcUtils;

import com.baidu.bookstore.order.dao.OrderDao;
import com.baidu.bookstore.order.domain.Order;
import com.baidu.bookstore.order.exception.OrderException;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	public void add(Order order){
		try{
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);
			orderDao.addOrderItemList(order.getOrderItemList());
			JdbcUtils.commitTransaction();
		}catch(SQLException e){
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				throw new RuntimeException();
			}
		}
	}
	public List<Order> myOrders(String uid) {
		return orderDao.findByUid(uid);
	}
	public Order load(String oid) {
		return orderDao.load(oid);
	}
	public void confirm(String oid) throws OrderException {
		Order order = orderDao.findByOid(oid);
		if(order.getState()!=3) throw new OrderException("确认失败，请先付款");
		orderDao.updateState(oid,4);
	}
	
	public void pay(String oid) {
		Order order = orderDao.findByOid(oid);
		if(order.getState()==1){
			orderDao.updateState(oid, 2);
		}
	}
	public List<Order> findAll() {
		return orderDao.findAll();
	}
}
