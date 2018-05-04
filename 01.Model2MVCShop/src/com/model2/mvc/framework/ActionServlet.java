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
		String resources=getServletConfig().getInitParameter("resources"); // web.xml�� com/model2/mvc/resources/actionmapping.properties
		mapper=RequestMapping.getInstance(resources);  //actionmapping.properties�� ��� �͵��� ��
		//init���� �� �ѹ� ȣ���̹Ƿ�, syncronized �ʿ� x service�� ������ synck~�������
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length()); //path : ���� URI (ex. logon.do)
		System.out.println(path);
		
		try{
			Action action = mapper.getAction(path);  // action => LoginAction 
			action.setServletContext(getServletContext()); //ǥ��ȭ�� ������ ��� ������ ����
			
			
			String resultPage=action.execute(request, response); // LoginAction �� execute �޼ҵ带 ���� (return �� : redirect:/index.jsp)
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