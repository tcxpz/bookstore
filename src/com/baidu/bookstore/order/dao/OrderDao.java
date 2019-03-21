package com.baidu.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.itcast.utils.CommonUtils;

import com.baidu.bookstore.book.domain.Book;
import com.baidu.bookstore.order.domain.Order;
import com.baidu.bookstore.order.domain.OrderItem;

public class OrderDao {
	private QueryRunner runner = new TxQueryRunner();
	
	public void addOrder(Order order){
		try{
			String sql = "insert into orders values(?,?,?,?,?,?)";
			Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
			Object[] params = {order.getOid(),timestamp,order.getTotal(),
					order.getState(),order.getUser().getUid(),order.getAddress()};
			runner.update(sql,params);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void addOrderItemList(List<OrderItem> orderItemList){
		try{
			String sql = "insert into orderitem values(?,?,?,?,?)";
			Object[][] params = new Object[orderItemList.size()][];
			for(int i=0;i<orderItemList.size();i++){
				OrderItem oi = orderItemList.get(i);
				params[i] = new Object[]{oi.getIid(),oi.getCount(),oi.getSubtotal(),oi.getOrder().getOid(),oi.getBook().getBid()};
			}
			runner.batch(sql, params);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public List<Order> findByUid(String uid) {
		try {
			String sql = "select * from orders where uid=?";
			List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class), uid);
			for(Order order : orderList) {
				loadOrderItems(order);//为order对象添加它的所有订单条目
			}
			return orderList;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map : mapList) {
			OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
			Book book = CommonUtils.toBean(map, Book.class);
			orderItem.setBook(book);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}

	private void loadOrderItems(Order order) throws SQLException {
		String sql = "select * from orderitem i, book b where i.bid=b.bid and oid=?";
		List<Map<String,Object>> mapList = runner.query(sql, new MapListHandler(), order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);//
		order.setOrderItemList(orderItemList);
	}
	
	public Order load(String oid) {
		try{
			String sql = "select * from orders where oid=?";
			Order order = runner.query(sql, new BeanHandler<Order>(Order.class),oid);
			loadOrderItems(order);//加载订单条目
			return order;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public Order findByOid(String oid) {
		try{
			String sql = "select * from orders where oid=?";
			return runner.query(sql, new BeanHandler<Order>(Order.class),oid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public void updateState(String oid,int state) {
		try{
			String sql = "update orders set state=? where oid=?";
			Object[] params ={state,oid};
			runner.update(sql,params);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
	}

	public List<Order> findAll() {
		try{
			String sql = "select * from orders";
			List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class));
			for(int i=0;i<orderList.size();i++){
				loadOrderItems(orderList.get(i));
			}
			return orderList;
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
	
	

}
