package com.baidu.bookstore.book.web.servlet.admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.utils.CommonUtils;

import com.baidu.bookstore.book.domain.Book;
import com.baidu.bookstore.book.service.BookService;
import com.baidu.bookstore.category.domain.Category;
import com.baidu.bookstore.category.service.CategoryService;

public class AdminAddBookServlet extends HttpServlet {
	private CategoryService  categoryService = new CategoryService();
	private BookService bookService = new BookService();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(40 * 1024);
		
		Book book = null;
		try {
			List<FileItem> fileItemList = sfu.parseRequest(request);
			Map<String, String> map = new HashMap<String, String>();
			for(FileItem fileItem:fileItemList){
				if(fileItem.isFormField()){
					map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
				}
			}
			book = CommonUtils.toBean(map, Book.class);
			book.setBid(CommonUtils.uuid());
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			
			
			String savePath = this.getServletContext().getRealPath("/book_img");
			String fileName = fileItemList.get(1).getName();
			//处理上传文件名称为绝对路径的问题
			int index = fileName.lastIndexOf("\\");
			if(index!=-1) {
				fileName = fileName.substring(index+1);
			}
			fileName = CommonUtils.uuid()+fileName;
			File destFile = new File(savePath,fileName);
			fileItemList.get(1).write(destFile);
			book.setImage("book_img/"+fileName);
			
			Map<String, String> exceptionMap = bookService.verify(book);
			if(exceptionMap.size()>0){
				destFile.delete();//删除这个文件！
				request.setAttribute("exceptionMap", exceptionMap);
				request.setAttribute("book", book);
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
				return;
			}
			bookService.add(book);
			request.setAttribute("msg", "添加成功");
			request.getRequestDispatcher("/adminjsps/admin/book/msg.jsp").forward(request, response); 
		
		} catch (Exception e) {
			if(e instanceof FileUploadBase.FileSizeLimitExceededException) {
				request.setAttribute("msg", "图片大于40KB!");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
			}
		}
		
	}

}
