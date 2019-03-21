package com.baidu.bookstore.book.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

import com.baidu.bookstore.book.domain.Book;
import com.baidu.bookstore.book.service.BookService;

public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 8309276380322955634L;
	BookService bookService = new BookService();
	//查询所有图书
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Book> bookList =  bookService.findAll();
		request.setAttribute("bookList", bookList);
		return "f:/jsps/book/list.jsp";
	}
	//按分类进行查询
	public String findByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		List<Book> bookList = bookService.findByCategory(cid);
		request.setAttribute("bookList",bookList);
		return "f:/jsps/book/list.jsp";
	}
	
	//加载某一本书的详细信息
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		Book book = bookService.findByBid(bid);
		request.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}

}
