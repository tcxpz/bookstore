package com.baidu.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;

import com.baidu.bookstore.user.domain.User;

public class UserDao {
	QueryRunner runner = new TxQueryRunner();

	public User findByUsername(String username) {
		try{
			String sql = "select * from user where username=?";
			return runner.query(sql, new BeanHandler<User>(User.class),username);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public User findByEmail(String email){
		try{
			String sql = "select * from user where email=?";
			return runner.query(sql, new BeanHandler<User>(User.class),email);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
				
	}

	public void add(User form){
		try{
			String sql = "insert into user values (?,?,?,?,?,?)";
			Object[] params = {form.getUid(),form.getUsername(),form.getPassword(),form.getEmail(),form.getCode(),form.isState()} ;
			runner.update(sql, params);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public User findByCode(String code){
		try {
			String sql = "select * from user where code=?";
			return runner.query(sql, new BeanHandler<User>(User.class),code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateState(String uid){
		try{
			String sql = "update user set state=true where uid=?";
			runner.update(sql,uid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

}
