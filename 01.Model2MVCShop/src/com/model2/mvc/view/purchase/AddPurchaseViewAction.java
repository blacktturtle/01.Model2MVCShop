package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class AddPurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("구매할 것의 상품번호 :  " + prodNo);
		
		ProductVO vo = new ProductVO();
		ProductService service = new ProductServiceImpl();
		vo = service.getProduct(prodNo);
		
		request.setAttribute("vo", vo);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
