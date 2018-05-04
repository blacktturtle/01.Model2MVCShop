<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
 <%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.product.vo.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%!boolean ������ư = false;
boolean ������ư = false; %>
<%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map"); // count(�� ���ڵ� ����)�� list(ProductVO�� ���)�� �޾ƿ� map
	SearchVO searchVO=(SearchVO)request.getAttribute("searchVO");
	
	

	
	int startPage = Integer.parseInt(request.getParameter("startPage"));
	System.out.println("���� startPage : " + startPage);
	int endPage = Integer.parseInt(request.getParameter("endPage"));
	System.out.println("���� endPage : " + endPage);
	int wantPage = searchVO.getWantPage();
	System.out.println("���� wantPage : " + wantPage);
	
	
	int total=0;
	ArrayList<ProductVO> list=null;
	if(map != null){
		total=((Integer)map.get("count")).intValue(); // �� ������ total�� ����
		list=(ArrayList<ProductVO>)map.get("list"); // ��� ���ڵ带 list�� ����
	}
	
	int currentPage=searchVO.getPage(); 
	
	int totalPage=0;
	if(total > 0) {
		//=====================
	
		if(searchVO.getPageUnit()*wantPage<total){
			������ư = true;
		}
		if(startPage == 1){
			������ư = false;
		}
	
		//=====================
		
		totalPage= total / searchVO.getPageUnit() ; //totalpage = ��ü���ڵ� �� / ����������(10���� �س���)
		if(total%searchVO.getPageUnit() >0)
			totalPage += 1;
		System.out.println("���� totalPage : " + totalPage);
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
					<td width="93%" class="ct_ttl01">��ǰ �����ȸ</td>
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
		if(searchVO.getSearchCondition() != null) { // �˻� ������ �������� ���
	%>
		
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%
				if(searchVO.getSearchCondition().equals("0")){
			%>
				<option value="0"selected>��ǰ��ȣ</option>
				<option value="1">��ǰ��</option>
				<option value="2">��ǰ����</option>
					<%
				}else if(searchVO.getSearchCondition().equals("1")){
		%>
				<option value="0">��ǰ��ȣ</option>
				<option value="1"selected>��ǰ��</option>
				<option value="2">��ǰ����</option>
		<%
				}else if(searchVO.getSearchCondition().equals("2")){ %>
					<option value="0">��ǰ��ȣ</option>
					<option value="1">��ǰ��</option>
					<option value="2"selected>��ǰ����</option>
					<%} %>
				
			</select>
			<input type="text" name="searchKeyword"   value="<%=searchVO.getSearchKeyword() %>"class="ct_input_g" style="width:200px; height:19px" />
		</td>
		<%
		}else{
	%>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0">��ǰ��ȣ</option>
				<option value="1">��ǰ��</option>
				<option value="2">��ǰ����</option>
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
						<a href="javascript:fncGetProductList();">�˻�</a>
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
		<td colspan="11" >��ü <%=total %> �Ǽ�, ���� <%=currentPage %> ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
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
		
			�Ǹ���
		
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
		
		<%if(������ư==true){ %>
	<a href="/listProduct.do?page=<%=startPage-wantPage%>&menu=search&startPage=<%=startPage-wantPage%>&endPage=<%=endPage-wantPage%>">����</a>
		<%}else{ %>
		<a>����</a>
		<%} %>
		
		
			<%for(int i=startPage; i<=endPage; i++){
				if(endPage==totalPage){
					������ư = false;
				}

				if(i > totalPage){
					������ư = false;
					break;
				}
				if(searchVO.getSearchCondition()==null||searchVO.getSearchKeyword()==null){ %>
				<%System.out.println(i + "�� ������."); %>
				<a href="/listProduct.do?page=<%=i%>&menu=search"><%=i %></a> <%--������ �ٲ� �� �ٽ� �ҷ��� --%>
				<%}else{ %>	
			<a href="/listProduct.do?page=<%=i%>&menu=search&searchKeyword=<%=searchVO.getSearchKeyword() %>&searchCondition=<%=searchVO.getSearchCondition()%>"><%=i %></a> <%--������ �ٲ� �� �ٽ� �ҷ��� --%>
			<%	}
				startPage = i+1;
				}
			
			%>
									
		<%if(������ư==true){ %>		
		
		<a href="/listProduct.do?page=<%=startPage%>&menu=search&startPage=<%=startPage%>&endPage=<%=endPage+wantPage%>">����</a>
		<%������ư=true; %>

		<%}else{ %>
		<a>����</a>
		<%} %>
		
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>