<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.service.purchase.vo.*" %>
<%@ page import="com.model2.mvc.service.user.vo.*" %>
<%@ page import="java.util.*"  %>

<% UserVO userVO = (UserVO)session.getAttribute("user"); %>    
		
<%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map"); // count(총 레코드 개수)와 list(ProductVO가 담긴)를 받아온 map
	SearchVO searchVO=(SearchVO)request.getAttribute("searchVO"); 
	
	int total=0;
	ArrayList<PurchaseVO> list=null;
	if(map != null){
		total=((Integer)map.get("count")).intValue(); // 총 갯수를 totla에 넣음
		list=(ArrayList<PurchaseVO>)map.get("list"); // 모든 레코드를 list에 넣음
	}
	
	int currentPage=searchVO.getPage(); 
	
	int totalPage=0;
	if(total > 0) {
		totalPage= total / searchVO.getPageUnit() ; //totalpage = 전체레코드 수 / 페이지유닛(10으로 해놨음)
		if(total%searchVO.getPageUnit() >0)
			totalPage += 1;
	}
%>

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList() {
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listUser.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 <%=total %> 건수, 현재 <%= currentPage %> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<% 	
		int no=list.size();
	System.out.println("no : " +no);
		for(int i=0; i<list.size(); i++) {
			PurchaseVO vo = (PurchaseVO)list.get(i);
	%>
	
	
	<tr class="ct_list_pop">
	<td align="center">
			<a href="/getPurchase.do?tranNo=<%=vo.getTranNo() %>"><%=no-- %></a>
		</td>
		<td></td>
		<td align="left">
			<a href="/getUser.do?userId=<%=userVO.getUserId()%>"><%=userVO.getUserId() %></a>
		</td>
		<td></td>
		<td align="left"><%=vo.getReceiverName() %></td>
		<td></td>
		<td align="left"><%=vo.getReceiverPhone() %></td>
		<td></td>
		<td align="left">
		<%System.out.println("구매상태코드 뭐야 : " + vo.getTranCode()); %>
		<%if(vo.getTranCode().trim().equals("1")){ %>
			현재 구매완료 상태 입니다.
		<%}else{%>
			아직 안만들었다(배송중? 배송완료 생겨아함)
		<%} %>
		
		
		</td>
		
		<td></td>
		<td align="left">
			
		</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<%} %>
	
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		
			<%for(int i=1;i<=totalPage;i++){%>
				
				<a href="/listPurchase.do?page=<%=i%>"><%=i %></a> <%--페이지 바꿀 시 다시 불러옴 --%>
		<%} %>	
		
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>