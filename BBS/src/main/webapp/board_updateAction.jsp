<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.Board" %>
<%@ page import="board.BoardDAO" %>
<!-- 지시자 : JSP 페이지가 컨테이너에게 필요한 메세지를 보내기 위한 태그 -->
<%@ page import ="java.io.PrintWriter"%>
<!-- JS작성하기위해 사용 -->
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="board" class="board.Board" scope="page"/>
<!-- scope="page" : 현재 페이지 안에서 사용 -->
<jsp:setProperty name="board" property="boardTitle"/>
<jsp:setProperty name="board" property="boardContent"/>
<!-- board테이블의 칼럼은 6개지만, 사용자가 직접 입력하는 칼럼은 2개뿐임 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
		//session 상태 체크 
		String userId = null;
		if(session.getAttribute("userId") != null){
			userId = (String)session.getAttribute("userId");
		}
		//누락부분 체크
		if( board.getBoardTitle() == null || board.getBoardContent() == null ){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력하지 않은 사항이 있습니다.');");
			script.println("history.back()");
			script.println("</script>");
		}else{
			int boardId 		= Integer.parseInt(request.getParameter("boardId"));
			String boardTitle 	= request.getParameter("boardTitle");
			String boardContent = request.getParameter("boardContent");
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			
			int result = new BoardDAO().update(boardId, boardTitle, boardContent);
			
			if(result == -1){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('게시글 수정에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
				
			}else {
				%>
				<script>
				alert('게시글 수정이 완료되었습니다.');
				location.href = 'board_detail.jsp?boardId=<%= boardId %>&pageNumber=<%=pageNumber%>';
				</script>
				<%
			}
		}
		
	%>
</body>
</html>