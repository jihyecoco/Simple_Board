<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 뷰포트의 너비가 웹 사이트를 보고있는 장치와 동일하다는 것을 정의 -->
<link rel="stylesheet" href="css/bootstrap.css">
<!-- font 적용 -->
<link rel="stylesheet" href="css/custom.css">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<nav class="navbar navbar-default"> 
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<!--  
				data-toggle="collapse"
				- 클릭 또는 특정 동작을 트리거로 하여 요소의 접힘(collapsing) 또는 펼침(expanding)을 제어하는 기능을 활성화

				data-target="#bs-example-navbar-collapse-1"
				- 접히거나 펼쳐질 요소의 대상(target)을 지정
				  여기서 #bs-example-navbar-collapse-1은 해당 대상 요소의 ID를 가리킨다. 
				  이 요소가 접히거나 펼쳐질 때, 이 코드가 연결된 트리거가 작동할 것이다.

				aria-expanded="false"
				- 웹 접근성을 위한 것으로, 요소의 접힘 또는 펼침 상태를 나타낸다. 
				  false는 해당 요소가 접혀져 있는 상태를 나타내며, 요소가 펼쳐지면 이 값은 true로 변경될 것이다.
				
				=>주로 탐색 모음(navbar) 등의 요소에서 사용되며, 
				클릭하거나 특정 동작을 통해 메뉴나 콘텐츠 영역을 접거나 펼치는 기능을 구현하는데 활용된다.
				-->
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">JSP 게시판 웹 사이트</a>
		</div>
		
		<div class="collapse navbar-collapse" id="bd-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li><a href="board.jsp">게시판</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" 
						aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li class="active"><a href="login.jsp">로그인</a></li>
						<!--  class="active" 현재 선택된 페이지라는 뜻 -->
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!-- 
		////
		id="bd-example-navbar-collapse-1"와 data-target="#bd-example-navbar-collapse-1"
		같은 값을 가져야 하는 이유
		- Bootstrap에서 요소의 접힘/펼침을 제어하기 위한 JavaScript 동작을 설정하기위해서,
		////

		id="bd-example-navbar-collapse-1"
		- HTML 요소에 부여되는 고유한 식별자(ID)입니다. 
		  해당 요소를 식별하고 조작하는 데 사용됩니다.
	
		data-target="#bd-example-navbar-collapse-1"
		- Bootstrap JavaScript 코드에 의해 사용되는 특수한 데이터 속성
		  특정 요소를 선택하고 해당 요소의 접힘/펼침 상태를 제어하는 데 사용된다. 
		  data-target에는 선택하려는 요소의 ID를 지정합니다.
	
		=> ID와 `data-target`을 같은 값으로 맞추는 것은 JavaScript에서 특정 요소를 찾아서 상호 작용하는 데 용이하며, 
		코드를 보다 직관적으로 만들어주는데 도움이 된다.
		-->
	</nav>
	
	<div class="container">
		<div class="col-log-4"></div>
		<div class="col-log-4">
			<div class="jumbotron" style="padding-top: 20px">
				<form method="post" action="joinAction.jsp">
					<h3 style="text-align: center">회원가입 화면</h3>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="아이디" name="userId" maxlength="20">
					</div>	
					<div class="form-group">
						<input type="password" class="form-control" placeholder="비밀번호" name="userPassword" maxlength="20">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="이름" name="userName" maxlength="20">
					</div>
					<div class="form-group" style="text-align: center">
						<div class="btn-group" data-toggle="buttons">
							<label class="btn btn-primary active">
								<input type="radio" name="userGender" autocomplete="off" value="남자" checked>남자
							</label>
							<label class="btn btn-primary">
								<input type="radio" name="userGender" autocomplete="off" value="여자" >여자
							</label>
						</div>
					</div>
					<div class="form-group">
						<input type="email" class="form-control" placeholder="이메일" name="userEmail" maxlength="50">
					</div>
					<input type="submit" class="btn btn-primary form-control" value="회원가입">
				</form>
			</div>
		</div>
		<div class="col-log-4"></div>
	</div>
	
	<script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>