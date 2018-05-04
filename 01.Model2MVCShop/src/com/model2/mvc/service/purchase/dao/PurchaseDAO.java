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

		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo()); // ��ǰ��ȣ
		stmt.setString(2, purchaseVO.getBuyer().getUserId()); // ����ID
		stmt.setString(3, purchaseVO.getPaymentOption()); // ���ҹ��
		stmt.setString(4, purchaseVO.getReceiverName()); // �޴»�� �̸�
		stmt.setString(5, purchaseVO.getReceiverPhone()); // �޴»�� ��ȣ
		stmt.setString(6, purchaseVO.getDivyAddr());// ������ּ�
		stmt.setString(7, purchaseVO.getDivyRequest()); // �䱸����
		stmt.setString(8, purchaseVO.getTranCode()); // ���Ż����ڵ�
		stmt.setString(9, purchaseVO.getDivyDate()); // ����������

		System.out.println("DB�� insert : " + purchaseVO);

		stmt.executeUpdate();

		con.close();
	}

	
	 public PurchaseVO findPurchase(int tranNo) throws Exception {
	//���Ź�ȣ�� ����
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
				ResultSet.TYPE_SCROLL_INSENSITIVE, // Ŀ�� �̵��� �����Ӱ� �ϰ� ������Ʈ ������
																								// �ݿ����� �ʴ´�.
				ResultSet.CONCUR_UPDATABLE); // �����ͺ����� �����ϵ��� �Ѵ�
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow(); // row�� ���ϱ�
		System.out.println("�ο��� ��:" + total);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total)); // (�Խñ� �� ������ count��)

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit() + 1); // absolute �ش� ������ �ٷ� �̵�(
																								// ��������ġ * �������Խñ� �� -
																								// �Խñ�+1) �� ù°�� �������� �б�
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) { // 9�� �߰� ������ 10���� �س��⶧��
				PurchaseVO purchaseVO = new PurchaseVO();
				purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
				purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));

				list.add(purchaseVO); // ����Ʈ�� �߰�
				if (!rs.next()) // rs.next �����ϹǷ� ���� �����ٷ� �̵� ������ ������ break;
					break;
			}
		}
		System.out.println("list.size() : " + list.size());
		map.put("list", list); // list put
		System.out.println("map().size() : " + map.size()); // count �� list �ΰ� ��

		con.close();

		return map; // count�� list�� ��� map ����
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