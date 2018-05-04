package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources"); // web.xml의 com/model2/mvc/resources/actionmapping.properties
		mapper=RequestMapping.getInstance(resources);  //actionmapping.properties의 모든 것들이 들어감
		//init으로 단 한번 호출이므로, syncronized 필요 x service에 들어갓으면 synck~해줘야함
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length()); //path : 접속 URI (ex. logon.do)
		System.out.println(path);
		
		try{
			Action action = mapper.getAction(path);  // action => LoginAction 
			action.setServletContext(getServletContext()); //표준화된 폴더의 모든 정보를 저장
			
			
			String resultPage=action.execute(request, response); // LoginAction 의 execute 메소드를 실행 (return 값 : redirect:/index.jsp)
			String result=resultPage.substring(resultPage.indexOf(":")+1); // result = index.jsp
			
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}