package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("user");
		
		SearchVO searchVO = new SearchVO();
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		searchVO.setPage(page);
		String pageUnit = getServletContext().getInitParameter("pageSize"); // 한 페이지에 몇개씩 보여줄 것인지
		searchVO.setPageUnit(Integer.parseInt(pageUnit)); // 10
		
		PurchaseService service = new PurchaseServiceImpl();
		HashMap<String, Object> map = service.getPurchaseList(searchVO, userVO.getUserId());

		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
		
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
