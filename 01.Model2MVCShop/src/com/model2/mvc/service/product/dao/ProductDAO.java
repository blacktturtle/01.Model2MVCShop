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
	
		stmt.setString(1, productVO.getProdName()); //상품명
		stmt.setString(2, productVO.getProdDetail()); //상품상세정보
		stmt.setString(3, productVO.getManuDate().replace("-", "")); //상품제조일자
		stmt.setInt(4, productVO.getPrice()); // 상품가격
		stmt.setString(5, productVO.getFileName()); //이미지파일
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
			if (searchVO.getSearchCondition().equals("0")) { // 0 : 상품번호일 경우
				sql += " where PROD_NO LIKE '%" + searchVO.getSearchKeyword()
						+ "%'";
			} else if (searchVO.getSearchCondition().equals("1")) { // 1 : 상품명일 경우
				sql += " where PROD_NAME LIKE '%" + searchVO.getSearchKeyword()
						+ "%'";
			}
			else if (searchVO.getSearchCondition().equals("2")) { // 1 : 상품가격일 경우
				sql += " where PRICE LIKE '%" + searchVO.getSearchKeyword()
						+ "%'";
			}
		}
		sql += "order by PROD_NO";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE, //커서 이동을 자유롭게 하고 업데이트 내용을 반영하지 않는다.
														
														ResultSet.CONCUR_UPDATABLE); //데이터변경이 가능하도록 한다
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow(); // row수 구하기
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total)); //(게시글 총 갯수를 count로)

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1); //absolute 해당 쿼리로 바로 이동( 페이지위치 * 페이지게시글 수 - 게시글+1) 즉 첫째줄 쿼리부터 읽기
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) { //9번 추가 유닛을 10으로 해놨기때문
				ProductVO productVO = new ProductVO();
				productVO.setProdNo(rs.getInt("PROD_NO"));
				productVO.setProdName(rs.getString("PROD_NAME"));
				productVO.setProdDetail(rs.getString("PROD_DETAIL"));
				productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
				productVO.setPrice(rs.getInt("PRICE"));
				productVO.setFileName(rs.getString("IMAGE_FILE"));
				productVO.setRegDate(rs.getDate("REG_DATE"));
				
				System.out.println(productVO);

				list.add(productVO); //리스트에 추가
				if (!rs.next()) //rs.next 실행하므로 쿼리 다음줄로 이동 쿼리가 없으면 break;
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list); // list put
		System.out.println("map().size() : "+ map.size()); // count 와 list 두개 들어감

		con.close();
			
		return map; // count와 list가 담긴 map 리턴
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