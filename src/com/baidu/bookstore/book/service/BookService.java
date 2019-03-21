package com.baidu.bookstore.book.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.jdbc.JdbcUtils;

import com.baidu.bookstore.book.dao.BookDao;
import com.baidu.bookstore.book.domain.Book;

public class BookService {
	BookDao bookDao = new BookDao();
	public List<Book> findAll(){
		return bookDao.findAll();
	}
	public List<Book> findByCategory(String cid) {
		return bookDao.findByCategory(cid);
	}
	public Book findByBid(String bid) {
		return bookDao.findByBid(bid);
	}
	public Map<String, Object> load(String bid) {
		return bookDao.load(bid);
	}
	public void add(Book book) {
		bookDao.add(book);
	}
	public Map<String,String> verify(Book book){
		Map<String,String> map = new HashMap<String,String>();
		if(book.getBname()==null||book.getBname().trim().isEmpty()){
			map.put("bname", "请输入图书名称");
		}
		if(book.getPrice()==0.0){
			map.put("price", "请输入图书单价");
		}
		if(book.getImage().length()==41){
			map.put("image", "请上传图书图片");
		}else if(!book.getImage().endsWith("jpg")) {
			map.put("image", "图片不是jpg格式");
		}
		if(book.getAuthor()==null||book.getAuthor().trim().isEmpty()){
			map.put("author", "请输入图书作者");
		}
		return map;
	}
	public void delete(String bid) {
		bookDao.update(bid);
	}
	public void edit(Book book) {
		try{
			JdbcUtils.beginTransaction();
			bookDao.update(book.getBid());
			bookDao.add(book);
			JdbcUtils.commitTransaction();
		}catch(Exception e){
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
		}
	}
}
