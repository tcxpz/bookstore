package com.baidu.bookstore.book.web.servlet.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;

import com.baidu.bookstore.book.domain.Book;
import com.baidu.bookstore.book.service.BookService;
import com.baidu.bookstore.category.domain.Category;
import com.baidu.bookstore.category.service.CategoryService;

public class AdminBookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Book> bookList = bookService.findAll();
		request.setAttribute("bookList", bookList);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		Map<String, Object> map = bookService.load(bid);
		Category category = CommonUtils.toBean(map, Category.class);
		Book book = CommonUtils.toBean(map, Book.class);
		book.setCategory(category);
		request.setAttribute("book", book);
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	public String addPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		return "f:/adminjsps/admin/book/add.jsp";
	}
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		bookService.delete(bid);
		return findAll(request,response);
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		book.setCategory(category);
		Map<String, String> exceptionMap = bookService.verify(book);
		if(exceptionMap.size()>0){
			request.setAttribute("exceptionMap", exceptionMap);
			request.setAttribute("book", book);
			request.setAttribute("categoryList", categoryService.findAll());
			return "f:/adminjsps/admin/book/desc.jsp";
		}
		bookService.edit(book);
		request.setAttribute("msg", "修改图书信息成功");
		return "f:/adminjsps/admin/book/msg.jsp";
	}

}
