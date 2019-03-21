package com.baidu.bookstore.cart.domain;

import java.math.BigDecimal;

import com.baidu.bookstore.book.domain.Book;

public class CartItem {
	private Book book;
	private int count;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	//利用java.math包下的BigDecimal类解决二进制计算误差问题
	public double getSubtotal(){
		BigDecimal d1 = BigDecimal.valueOf(book.getPrice());
		BigDecimal d2 = BigDecimal.valueOf(count);
		BigDecimal subtotal = d1.multiply(d2);
		return subtotal.doubleValue();
	}
	@Override
	public String toString() {
		return "CartItem [book=" + book + ", count=" + count + "]";
	}
}
