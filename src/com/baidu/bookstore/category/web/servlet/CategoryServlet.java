package com.baidu.bookstore.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

import com.baidu.bookstore.category.domain.Category;
import com.baidu.bookstore.category.service.CategoryService;

public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 3988103593734631291L;
	CategoryService categoryService = new CategoryService();

	public void findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/jsps/left.jsp").forward(request, response);
	}

}
