package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class CheckDuplicationAction extends Action{ 

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		String userId=request.getParameter("userId"); 
		
		UserService service=new UserServiceImpl();  // Dao 불러옴
		boolean result=service.checkDuplication(userId); // find user 후 true or false 리턴 (중복 아이디인지 아닌지)
		
		request.setAttribute("result",new Boolean(result)); 
		request.setAttribute("userId", userId);
		
		return "forward:/user/checkDuplication.jsp";
	}
}