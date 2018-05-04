package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String a = request.getParameter("prodNo");
		int prodNo = Integer.parseInt(a);
		System.out.println("받아온 번호는?" + prodNo);

		ProductService service = new ProductServiceImpl();
		ProductVO vo = service.getProduct(prodNo);

		request.setAttribute("vo", vo);
		
		if(request.getParameter("menu").equals("search")) {
			return "forward:/product/getProduct.jsp?menu=search";	
		}else {
			return "forward:/product/getProduct.jsp";	
		}


	}

}
