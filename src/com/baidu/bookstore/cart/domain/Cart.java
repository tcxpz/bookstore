package com.baidu.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	Map<String,CartItem> map = new LinkedHashMap<String, CartItem>();//以bid作为键，以CartItem作为值
	
	//添加条目到车中
	public void addItem(CartItem cartItem){
		String bid = cartItem.getBook().getBid(); 
		if(map.containsKey(bid)){   //如果map中已经存在该条目
			map.get(bid).setCount(map.get(bid).getCount()+cartItem.getCount());
		}else{
			map.put(bid, cartItem);
		}
	}
	
	//清除车中所有条目
	public void clearAll(){
		map.clear();
	}
	
	//删除车中指定条目
	public void deleteItem(String bid){
		map.remove(bid);
	}
	
	//获取所有条目
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	//获得总计金额
	public double getTotal(){
		BigDecimal sum = BigDecimal.valueOf(0);
		for(CartItem cartItem:map.values()){
			BigDecimal subTotal = BigDecimal.valueOf(cartItem.getSubtotal());
			sum = sum.add(subTotal); 
		}
		return sum.doubleValue();
	}
}
