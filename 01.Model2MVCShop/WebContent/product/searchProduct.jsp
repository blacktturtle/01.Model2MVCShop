<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
 <%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.product.vo.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%!boolean 이전버튼 = false;
boolean 다음버튼 = false; %>
<%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map"); // count(총 레코드 개수)와 list(ProductVO가 담긴)를 받아온 map
	SearchVO searchVO=(SearchVO)request.getAttribute("searchVO");
	
	

	
	int startPage = Integer.parseInt(request.getParameter("startPage"));
	System.out.println("현재 startPage : " + startPage);
	int endPage = Integer.parseInt(request.getParameter("endPage"));
	System.out.println("현재 endPage : " + endPage);
	int wantPage = searchVO.getWantPage();
	System.out.println("현재 wantPage : " + wantPage);
	
	
	int total=0;
	ArrayList<ProductVO> list=null;
	if(map != null){
		total=((Integer)map.get("count")).intValue(); // 총 갯수를 total에 넣음
		list=(ArrayList<ProductVO>)map.get("list"); // 모든 레코드를 list에 넣음
	}
	
	int currentPage=searchVO.getPage(); 
	
	int totalPage=0;
	if(total > 0) {
		//=====================
	
		if(searchVO.getPageUnit()*wantPage<total){
			다음버튼 = true;
		}
		if(startPage == 1){
			이전버튼 = false;
		}
	
		//=====================
		
		totalPage= total / searchVO.getPageUnit() ; //totalpage = 전체레코드 수 / 페이지유닛(10으로 해놨음)
		if(total%searchVO.getPageUnit() >0)
			totalPage += 1;
		System.out.println("현재 totalPage : " + totalPage);
	}
%>


<html>
<head>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
function fncGetProductList(){
	document.detailForm.submit();
}
-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">


<form name="detailForm" action="/listProduct.do?menu=search"method="post">
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	<%
		if(searchVO.getSearchCondition() != null) { // 검색 조건을 선택했을 경우
	%>
		
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%
				if(searchVO.getSearchCondition().equals("0")){
			%>
				<option value="0"selected>상품번호</option>
				<option value="1">상품명</option>
				<option value="2">상품가격</option>
					<%
				}else if(searchVO.getSearchCondition().equals("1")){
		%>
				<option value="0">상품번호</option>
				<option value="1"selected>상품명</option>
				<option value="2">상품가격</option>
		<%
				}else if(searchVO.getSearchCondition().equals("2")){ %>
					<option value="0">상품번호</option>
					<option value="1">상품명</option>
					<option value="2"selected>상품가격</option>
					<%} %>
				
			</select>
			<input type="text" name="searchKeyword"   value="<%=searchVO.getSearchKeyword() %>"class="ct_input_g" style="width:200px; height:19px" />
		</td>
		<%
		}else{
	%>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0">상품번호</option>
				<option value="1">상품명</option>
				<option value="2">상품가격</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" >
		</td>
		<%
		}
	%>
	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 <%=total %> 건수, 현재 <%=currentPage %> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<% 	
		int no=list.size();
	System.out.println("no : " +no);
		for(int i=0; i<list.size(); i++) {
			ProductVO vo = (ProductVO)list.get(i);
	%>
		
	<tr class="ct_list_pop">
		<td align="center"><%=no-- %></td>
		<td></td>
		
				<td align="left">
					<a href="/getProduct.do?prodNo=<%=vo.getProdNo() %>&menu=search"><%= vo.getProdName() %></a>
					
				</td>
		
		<td></td>
		<td align="left"><%=vo.getPrice() %></td>
		<td></td>
		<td align="left"><%=vo.getRegDate() %></td>
		<td></td>
		<td align="left">
		
			판매중
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	<%} %>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		
		<%if(이전버튼==true){ %>
	<a href="/listProduct.do?page=<%=startPage-wantPage%>&menu=search&startPage=<%=startPage-wantPage%>&endPage=<%=endPage-wantPage%>">이전</a>
		<%}else{ %>
		<a>이전</a>
		<%} %>
		
		
			<%for(int i=startPage; i<=endPage; i++){
				if(endPage==totalPage){
					다음버튼 = false;
				}

				if(i > totalPage){
					다음버튼 = false;
					break;
				}
				if(searchVO.getSearchCondition()==null||searchVO.getSearchKeyword()==null){ %>
				<%System.out.println(i + "번 눌렀다."); %>
				<a href="/listProduct.do?page=<%=i%>&menu=search"><%=i %></a> <%--페이지 바꿀 시 다시 불러옴 --%>
				<%}else{ %>	
			<a href="/listProduct.do?page=<%=i%>&menu=search&searchKeyword=<%=searchVO.getSearchKeyword() %>&searchCondition=<%=searchVO.getSearchCondition()%>"><%=i %></a> <%--페이지 바꿀 시 다시 불러옴 --%>
			<%	}
				startPage = i+1;
				}
			
			%>
									
		<%if(다음버튼==true){ %>		
		
		<a href="/listProduct.do?page=<%=startPage%>&menu=search&startPage=<%=startPage%>&endPage=<%=endPage+wantPage%>">다음</a>
		<%이전버튼=true; %>

		<%}else{ %>
		<a>다음</a>
		<%} %>
		
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
