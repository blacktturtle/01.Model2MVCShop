package com.model2.mvc.view.product;

import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {
	int startPage;
	int endPage;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SearchVO searchVO = new SearchVO();
		System.out.println("start : " + startPage);
		System.out.println("end : " + endPage);

		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		if (request.getParameter("startPage") != null) {	
			startPage = Integer.parseInt(request.getParameter("startPage"));
		}
		
		if (request.getParameter("endPage") != null) {
			endPage = Integer.parseInt(request.getParameter("endPage"));
		}
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		System.out.println("���� ���� : " +request.getParameter("searchCondition") );
		
		int wantPage = Integer.parseInt(getServletContext().getInitParameter("wantPage")); // �� �������� ��� ������ ������
		String pageUnit = getServletContext().getInitParameter("pageSize"); // �� �������� ��� ������ ������
		searchVO.setWantPage(wantPage);
		searchVO.setPageUnit(Integer.parseInt(pageUnit)); // 10

		ProductService service = new ProductServiceImpl();
		HashMap<String, Object> map = service.getProductList(searchVO);

		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
		
		if (request.getParameter("menu").equals("manage")) {
			System.out.println("mange�� �Ե�");
			return "forward:/product/manageProduct.jsp";
		} else {
			System.out.println("��ġ�οӴ�");
			return "forward:/product/searchProduct.jsp?startPage="+startPage+"&endPage="+endPage;
		}
		
	}

}
