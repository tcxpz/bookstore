package com.baidu.bookstore.user.service;

import com.baidu.bookstore.user.dao.UserDao;
import com.baidu.bookstore.user.domain.User;
import com.baidu.bookstore.user.exception.UserException;

public class UserService {
	UserDao dao = new UserDao();
	public void regist(User form) throws UserException {
		//校验username是否已被注册，如果已被注册则抛出异常
		User user = dao.findByUsername(form.getUsername());
		if(user != null) throw new UserException("用户名已被注册！");
		
		user = dao.findByEmail(form.getEmail());
		if(user!=null) throw new UserException("邮箱已被注册");
		
		dao.add(form);
	}
	
	public void active(String code) throws UserException{
		User user = dao.findByCode(code);
		if(user==null) throw new UserException("激活码无效");
		if(user.isState()){
			throw new UserException("您已经激活了，请勿重复激活");
		}
		dao.updateState(user.getUid());
	}
	public User login(User form) throws UserException{
		User user = dao.findByUsername(form.getUsername());
		if(user==null) throw new UserException("用户不存在，请先注册");
		if(!user.getPassword().equals(form.getPassword()))
			throw new UserException("密码不正确，请重新输入");
		if(!user.isState()) throw new UserException("尚未激活，请先登录邮箱激活");
		return user;
	}
}
