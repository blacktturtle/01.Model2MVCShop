package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;


public class UpdateUserAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		String userId=(String)request.getParameter("userId");
		
		UserVO userVO=new UserVO();
		userVO.setUserId(userId);
		userVO.setUserName(request.getParameter("userName"));
		userVO.setAddr(request.getParameter("addr"));
		userVO.setPhone(request.getParameter("phone"));
		userVO.setEmail(request.getParameter("email"));
		
		UserService service=new UserServiceImpl();
		service.updateUser(userVO);
		
		HttpSession session=request.getSession(); //세션 하나 만들고
		String sessionId=((UserVO)session.getAttribute("user")).getUserId(); //sessionId에 기존의 것을 넣고 
	
		if(sessionId.equals(userId)){ //기존의 것과 sessionid 와 id가 같음녀
			session.setAttribute("user", userVO); // 새로운 세션에 userVO 넣는다.
		}
		
		return "redirect:/getUser.do?userId="+userId;
	}
}