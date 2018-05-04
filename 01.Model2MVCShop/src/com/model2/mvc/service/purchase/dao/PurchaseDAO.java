package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;

public class PurchaseDAO {
	

	public PurchaseDAO() {
	}

	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction values (seq_product_prod_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo()); // 상품번호
		stmt.setString(2, purchaseVO.getBuyer().getUserId()); // 유저ID
		stmt.setString(3, purchaseVO.getPaymentOption()); // 지불방식
		stmt.setString(4, purchaseVO.getReceiverName()); // 받는사람 이름
		stmt.setString(5, purchaseVO.getReceiverPhone()); // 받는사람 번호
		stmt.setString(6, purchaseVO.getDivyAddr());// 배송지주소
		stmt.setString(7, purchaseVO.getDivyRequest()); // 요구사항
		stmt.setString(8, purchaseVO.getTranCode()); // 구매상태코드
		stmt.setString(9, purchaseVO.getDivyDate()); // 배송희망일자

		System.out.println("DB에 insert : " + purchaseVO);

		stmt.executeUpdate();

		con.close();
	}

	
	 public PurchaseVO findPurchase(int tranNo) throws Exception {
	//구매번호를 받음
	 Connection con = DBUtil.getConnection();
	
	 String sql = "select * from Transaction where TRAN_NO=?";
	
	 PreparedStatement stmt = con.prepareStatement(sql);
	 stmt.setInt(1, tranNo);
	
	 ResultSet rs = stmt.executeQuery();
	
	 PurchaseVO purchaseVO = null;
	 while (rs.next()) {
	 purchaseVO = new PurchaseVO();
	 ProductDAO productDAO = new ProductDAO();
	 UserDAO userDAO = new UserDAO();
	 
	 purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
	 purchaseVO.setPurchaseProd(productDAO.findProduct(rs.getInt("PROD_NO")));
	 purchaseVO.setBuyer(userDAO.findUser(rs.getString("BUYER_ID")));
	 purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
	 purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
	 purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
	 purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
	 purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
	 purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
	 purchaseVO.setOrderDate(rs.getDate("ORDER_DATA"));
	 purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));
	 
	 }
	
	 con.close();
	
	 return purchaseVO;
	 }
	
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where BUYER_ID= '"+ buyerId +"'";		

		sql += " order by TRAN_NO";

		PreparedStatement stmt = con.prepareStatement(sql, 
				ResultSet.TYPE_SCROLL_INSENSITIVE, // 커서 이동을 자유롭게 하고 업데이트 내용을
																								// 반영하지 않는다.
				ResultSet.CONCUR_UPDATABLE); // 데이터변경이 가능하도록 한다
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow(); // row수 구하기
		System.out.println("로우의 수:" + total);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total)); // (게시글 총 갯수를 count로)

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit() + 1); // absolute 해당 쿼리로 바로 이동(
																								// 페이지위치 * 페이지게시글 수 -
																								// 게시글+1) 즉 첫째줄 쿼리부터 읽기
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) { // 9번 추가 유닛을 10으로 해놨기때문
				PurchaseVO purchaseVO = new PurchaseVO();
				purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
				purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));

				list.add(purchaseVO); // 리스트에 추가
				if (!rs.next()) // rs.next 실행하므로 쿼리 다음줄로 이동 쿼리가 없으면 break;
					break;
			}
		}
		System.out.println("list.size() : " + list.size());
		map.put("list", list); // list put
		System.out.println("map().size() : " + map.size()); // count 와 list 두개 들어감

		con.close();

		return map; // count와 list가 담긴 map 리턴
	}
	
	 public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
	
	 Connection con = DBUtil.getConnection();
	 
	 String sql ="UPDATE transaction SET PAYMENT_OPTION=?, RECEIVER_NAME= ?, RECEIVER_PHONE =?, DEMAILADDR=?,DLVY_REQUEST=? ,DLVY_DATE=? where TRAN_NO=?";
	
	 PreparedStatement stmt = con.prepareStatement(sql);
	 stmt.setString(1, purchaseVO.getPaymentOption());
	 stmt.setString(2, purchaseVO.getReceiverName());
	 stmt.setString(3, purchaseVO.getReceiverPhone());
	 stmt.setString(4, purchaseVO.getDivyAddr());
	 stmt.setString(5, purchaseVO.getDivyRequest());
	 stmt.setString(6, purchaseVO.getDivyDate());
	 stmt.setInt(7, purchaseVO.getTranNo());
	 stmt.executeUpdate();
	
	 con.close();
	 }
}