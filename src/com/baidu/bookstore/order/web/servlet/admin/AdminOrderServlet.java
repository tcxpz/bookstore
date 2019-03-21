package com.baidu.bookstore.order.web.servlet.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.baidu.bookstore.order.domain.Order;
import com.baidu.bookstore.order.domain.OrderItem;
import com.baidu.bookstore.order.service.OrderService;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			List<Order> orderList = orderService.findAll();
			System.out.println(orderList);
			request.setAttribute("orderList", orderList);
			return "f:/adminjsps/admin/order/list.jsp";
	}

}
