package com.baidu.bookstore.category.service;

import java.util.List;

import org.apache.commons.fileupload.FileItem;

import com.baidu.bookstore.book.dao.BookDao;
import com.baidu.bookstore.book.domain.Book;
import com.baidu.bookstore.category.dao.CategoryDao;
import com.baidu.bookstore.category.domain.Category;
import com.baidu.bookstore.category.exception.CategoryException;

public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	private BookDao bookDao = new BookDao();

	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	public Category findByCid(String cid) {
		return categoryDao.findByCid(cid);
	}

	public void mod(Category category) {
		categoryDao.update(category);
	}

	public void add(Category category) {
		categoryDao.addCategory(category);
	}

	public void delete(String cid) throws CategoryException {
		int count = bookDao.countByCid(cid);
		if(count>0) throw new CategoryException("该分类下还有图书，不能删除！");
		categoryDao.deleteCategory(cid);
	}
}
