package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;


public class ProductDAO {
	
	public ProductDAO(){
	}

	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into product values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
	
		stmt.setString(1, productVO.getProdName()); //��ǰ��
		stmt.setString(2, productVO.getProdDetail()); //��ǰ������
		stmt.setString(3, productVO.getManuDate().replace("-", "")); //��ǰ��������
		stmt.setInt(4, productVO.getPrice()); // ��ǰ����
		stmt.setString(5, productVO.getFileName()); //�̹�������
		stmt.executeUpdate();
		
		
		
		con.close();
	}

	public ProductVO findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from Product where prod_No=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
			

		}
		
		con.close();

		return productVO;
	}
	

	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from product ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) { // 0 : ��ǰ��ȣ�� ���
				sql += " where PROD_NO LIKE '%" + searchVO.getSearchKeyword()
						+ "%'";
			} else if (searchVO.getSearchCondition().equals("1")) { // 1 : ��ǰ���� ���
				sql += " where PROD_NAME LIKE '%" + searchVO.getSearchKeyword()
						+ "%'";
			}
			else if (searchVO.getSearchCondition().equals("2")) { // 1 : ��ǰ������ ���
				sql += " where PRICE LIKE '%" + searchVO.getSearchKeyword()
						+ "%'";
			}
		}
		sql += "order by PROD_NO";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE, //Ŀ�� �̵��� �����Ӱ� �ϰ� ������Ʈ ������ �ݿ����� �ʴ´�.
														
														ResultSet.CONCUR_UPDATABLE); //�����ͺ����� �����ϵ��� �Ѵ�
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow(); // row�� ���ϱ�
		System.out.println("�ο��� ��:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total)); //(�Խñ� �� ������ count��)

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1); //absolute �ش� ������ �ٷ� �̵�( ��������ġ * �������Խñ� �� - �Խñ�+1) �� ù°�� �������� �б�
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) { //9�� �߰� ������ 10���� �س��⶧��
				ProductVO productVO = new ProductVO();
				productVO.setProdNo(rs.getInt("PROD_NO"));
				productVO.setProdName(rs.getString("PROD_NAME"));
				productVO.setProdDetail(rs.getString("PROD_DETAIL"));
				productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
				productVO.setPrice(rs.getInt("PRICE"));
				productVO.setFileName(rs.getString("IMAGE_FILE"));
				productVO.setRegDate(rs.getDate("REG_DATE"));
				
				System.out.println(productVO);

				list.add(productVO); //����Ʈ�� �߰�
				if (!rs.next()) //rs.next �����ϹǷ� ���� �����ٷ� �̵� ������ ������ break;
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list); // list put
		System.out.println("map().size() : "+ map.size()); // count �� list �ΰ� ��

		con.close();
			
		return map; // count�� list�� ��� map ����
	}

	public void updateUser(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?, IMAGE_FILE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
	
}