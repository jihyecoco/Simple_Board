<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="user.UserDAO"%>
<!-- 지시자 : JSP 페이지가 컨테이너에게 필요한 메세지를 보내기 위한 태그 -->
<%@ page import ="java.io.PrintWriter"%>
<!-- JS작성하기위해 사용 -->
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="user.User" scope="page"/>
<!-- scope="page" : 현재 페이지 안에서 사용 -->
<jsp:setProperty name="user" property="userId"/>
<jsp:setProperty name="user" property="userPassword"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<!-- 사용자의 로그인 시도를 처리하는 페이지 -->
<body>
	<%
		UserDAO userDAO = new UserDAO();
		int result = userDAO.login(user.getUserId(), user.getUserPassword());
	
		if(result == 1){
			session.setAttribute("userId", user.getUserId());
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
			
		}else if(result == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('비밀번호가 틀립니다.')");
			script.println("history.back()");
			script.println("</script>");
			
		}else if(result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('존재하지않는 아이디입니다.')");
			script.println("history.back()");
			script.println("</script>");
			
		}else if(result == -2){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
	%>
</body>
</html>