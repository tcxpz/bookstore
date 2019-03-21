package com.baidu.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.itcast.utils.CommonUtils;

import com.baidu.bookstore.book.domain.Book;

public class BookDao {
	QueryRunner runner = new TxQueryRunner();
	
	
	//查询所有图书
	public List<Book> findAll(){
		try{
			String sql = "select * from book where del=0";
			return runner.query(sql, new BeanListHandler<Book>(Book.class));
		}catch(SQLException e){
			throw  new RuntimeException(e);
		}
	}
	//按照分类查询图书
	public List<Book> findByCategory(String cid) {
		try{
			String sql = "select * from book where cid=? and del=0";
			return runner.query(sql, new BeanListHandler<Book>(Book.class),cid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
	}

	//查询某一本书的详细信息
	public Book findByBid(String bid) {
		try{
			String sql = "select * from book where bid=?";
			return runner.query(sql, new BeanHandler<Book>(Book.class),bid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	//查询某个分类下是否还有图书，当有图书时，不能直接删除分类
	public int countByCid(String cid) {
		try{
			String sql = "select count(*) from book where cid=? and del=0";
			Number num = (Number) runner.query(sql, new ScalarHandler(),cid);
			return num.intValue();
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}


	public Map<String, Object> load(String bid) {
		try{
			String sql = "select * from book b,category c where b.cid=c.cid and bid=?";
			return runner.query(sql, new MapHandler(),bid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}


	public void add(Book book) {
		try{
			String sql = "insert into book values(?,?,?,?,?,?,?)";
			Object[] params = {CommonUtils.uuid(),book.getBname(),book.getPrice(),book.getAuthor(),
					book.getImage(),book.getCategory().getCid(),0};
			runner.update(sql,params);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	public void update(String bid) {
		try{
			String sql = "update book set del=1 where bid=?";
			runner.update(sql,bid);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}
