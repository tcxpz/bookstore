package com.baidu.bookstore.cart.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

import com.baidu.bookstore.book.domain.Book;
import com.baidu.bookstore.book.service.BookService;
import com.baidu.bookstore.cart.domain.Cart;
import com.baidu.bookstore.cart.domain.CartItem;

public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = -4597247435676670235L;

	public String addItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		int count = Integer.parseInt(request.getParameter("count"));
		CartItem cartItem = new CartItem(); 
		Book book = new BookService().findByBid(bid);
		cartItem.setBook(book);
		cartItem.setCount(count);
		cart.addItem(cartItem);
		return "/jsps/cart/list.jsp";
	}
	public String clearAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clearAll();
		return "/jsps/cart/list.jsp";
	}
	
	public String deleteItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.deleteItem(bid);
		return "/jsps/cart/list.jsp";
	}

}
