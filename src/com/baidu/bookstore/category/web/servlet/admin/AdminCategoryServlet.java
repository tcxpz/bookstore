package com.baidu.bookstore.category.web.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;

import com.baidu.bookstore.category.domain.Category;
import com.baidu.bookstore.category.exception.CategoryException;
import com.baidu.bookstore.category.service.CategoryService;

public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		return "f:/adminjsps/admin/category/list.jsp";
	}
	public String modPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		Category category = categoryService.findByCid(cid);
		request.setAttribute("category", category);
		return "f:/adminjsps/admin/category/mod.jsp";
	}
	public String mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		categoryService.mod(category);
		return findAll(request,response);
	}
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = CommonUtils.uuid();
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		categoryService.add(category);
		return findAll(request,response);
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		try{
			categoryService.delete(cid);
		}catch(CategoryException e){
			request.setAttribute("msg",e.getMessage());
			return "f:/adminjsps/admin/msg.jsp";
		}
		return findAll(request,response);
	}

}
