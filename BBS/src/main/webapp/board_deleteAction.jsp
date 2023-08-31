<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 지시자 : JSP 페이지가 컨테이너에게 필요한 메세지를 보내기 위한 태그 -->
<%@ page import ="java.io.PrintWriter"%>
<%@ page import="board.Board" %>
<%@ page import="board.BoardDAO" %>
<!-- JS작성하기위해 사용 -->
<% request.setCharacterEncoding("UTF-8"); %>
<!-- scope="page" : 현재 페이지 안에서 사용 -->
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
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		
		Board board = new BoardDAO().getBoard(boardId);
		
		//System.out.println(userId);
		//System.out.println(board.getUserId());
		
		if(! (userId.equals(board.getUserId())) ){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.');");
			script.println("history.back()");
			script.println("</script>");
		}
			
		if( boardId <= 0 ){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 게시글입니다.');");
			script.println("history.back()");
			script.println("</script>");
		}else if(boardId == board.getBoardId()){
			//삭제결과
			int result = new BoardDAO().delete(boardId);
			//전체게시글 COUNT
			int totalBoardCount = new BoardDAO().totalBoardCount();
			//한 페이지에 보여주고자 하는 컨텐츠의 개수
			int pageSize = 5;
			if(result == -1){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('게시글 삭제에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
				
			}else {
				%>
				
				<script>
				alert('게시글 삭제가 완료되었습니다.');
				<%
				//총 페이지 수 = Math.ceil(전체 컨텐츠 개수 / 한 페이지에 보여주고자 하는 컨텐츠의 개수)
				int pageCount = (int)Math.ceil(totalBoardCount/pageSize);
				if(pageCount>pageNumber){
				%>	
					location.href = 'board.jsp?&pageNumber=<%=pageNumber%>';
				<%
				}else{
				%>
					location.href = 'board.jsp?&pageNumber=<%=pageNumber -1%>';
				<%
				}
				%>
				</script>
				<%
			}
		}
		
	%>
</body>
</html>