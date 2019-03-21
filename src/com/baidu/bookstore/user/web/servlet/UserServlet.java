package com.baidu.bookstore.user.web.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

import com.baidu.bookstore.cart.domain.Cart;
import com.baidu.bookstore.user.domain.User;
import com.baidu.bookstore.user.exception.UserException;
import com.baidu.bookstore.user.service.UserService;

/**
 * User表述层
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 8828670528729094095L;
	private UserService userService = new UserService();
	
	//注册功能
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User form = new User();
		form.setUid(CommonUtils.uuid());
		form.setUsername(request.getParameter("username"));
		form.setPassword(request.getParameter("password"));
		form.setEmail(request.getParameter("email"));
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		form.setState(false);

		Map<String,String> errors = new HashMap<String,String>();
		String username = form.getUsername();
		if(username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空！");
		} else if(username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度必须在3~10之间！");
		}
		
		String password = form.getPassword();
		if(password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空！");
		} else if(password.length() < 6 || password.length() > 10) {
			errors.put("password", "密码长度必须在6~10之间！");
		}
		
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "Email格式错误！");
		}
		if(errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
		try {
			userService.regist(form);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("email.properties"));
		String host = props.getProperty("host");//获取服务器主机
		String uname = props.getProperty("uname");//获取用户名
		String pwd = props.getProperty("password");//获取密码
		String from = props.getProperty("from");//获取发件人
		String to = form.getEmail();//获取收件人
		String subject = props.getProperty("subject");//获取主题
		String content = props.getProperty("content");//获取邮件内容
		content = MessageFormat.format(content, form.getCode());//替换{0}
		
		Session session = MailUtils.createSession(host, uname, pwd);//得到session
		Mail mail = new Mail(from, to, subject, content);//创建邮件对象
		try {
			MailUtils.send(session, mail);//发邮件！
		} catch (MessagingException e) {
		}
		request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱激活");
		return "f:/jsps/msg.jsp";
	}
	//激活功能
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			userService.active(code);
			request.setAttribute("msg", "恭喜您，激活成功！");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}
	//登录功能
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User form = new User();
		form.setUsername(request.getParameter("username"));
		form.setPassword(request.getParameter("password"));
		try {
			User user = userService.login(form);
			request.getSession().setAttribute("session_user", user);
			request.getSession().setAttribute("cart", new Cart());
			return "r:/jsps/main.jsp";
		} catch (UserException e) {
			request.setAttribute("form", form);
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/user/login.jsp";
		}
	}
	//退出功能
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/jsps/main.jsp";
	}
	
	
}
