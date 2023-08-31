<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
	session.invalidate();
	/* 세션정보 초기화하기
	session.invalidate();
	이 코드는 세션의 모든 속성을 제거하는 역할을 한다 */
	PrintWriter script = response.getWriter();
	/* 
	//alert 사용하기
	script.println("<script>");
	script.println("alert('로그아웃 되었습니다.')");
	script.println("</script>");
	 */
	//confirm 사용하기
	script.println("<script>");
	script.println("if (confirm('로그아웃 하시겠습니까?')){alert('로그아웃되었습니다.'); location.href = 'main.jsp'; }else{history.go(-1)}");
	script.println("</script>");
	%>
	<!-- <script type="text/javascript">
		location.href = "main.jsp";
	</script> -->
</body>
</html>