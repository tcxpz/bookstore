package com.baidu.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;

import com.baidu.bookstore.category.domain.Category;

public class CategoryDao {
	private QueryRunner runner = new TxQueryRunner();

	public List<Category> findAll() {
		try {
			String sql = "select * from category";
			return runner.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Category findByCid(String cid) {
		try{
			String sql = "select * from category where cid=?";
			return runner.query(sql, new BeanHandler<Category>(Category.class),cid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public void update(Category category) {
		try{
			String sql = "update category set cname=? where cid=?";
			Object[] params ={category.getCname(),category.getCid()};
			runner.update(sql,params);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public void addCategory(Category category) {
		try{
			String sql = "insert into category values(?,?)";
			runner.update(sql,category.getCid(),category.getCname());	
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public void deleteCategory(String cid) {
		try{
			String sql = "delete from category where cid=?";
			runner.update(sql,cid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}
