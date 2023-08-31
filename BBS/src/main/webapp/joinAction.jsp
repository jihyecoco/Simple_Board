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
<jsp:setProperty name="user" property="userName"/>
<jsp:setProperty name="user" property="userGender"/>
<jsp:setProperty name="user" property="userEmail"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<!-- 사용자의 로그인 시도를 처리하는 페이지 -->
<body>
	<%
		if( user.getUserId() 		== null || 
			user.getUserPassword() 	== null ||
			user.getUserName() 		== null ||
			user.getUserGender() 	== null ||
			user.getUserEmail() 	== null	){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력하지 않은 사항이 있습니다.');");
			script.println("history.go(-1)");
			script.println("</script>");
		}else{
			UserDAO userDAO = new UserDAO();
			int result = userDAO.join(user);
		
			if(result == 1){
				session.setAttribute("userId", user.getUserId());
				//세션 속성명이 이름인 속성에 속성값으로 값을 할당한다.
				//session.setAttribute("이름", 값);
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('회원가입이 완료되었습니다.')");
				script.println("location.href = 'main.jsp'");
				script.println("</script>");
				
			}else {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('회원가입에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
				
			}
		}
		
	%>
</body>
</html>