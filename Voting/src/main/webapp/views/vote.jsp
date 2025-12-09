<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
<%@ page import="domain.Vote"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Голосование</title>

<link rel="stylesheet" type="text/css" href="css/style.css"> 

<!-- Bootstrap CSS --> 
<link rel="stylesheet" href="css/bootstrap.min.css"> 
<!-- jQuery --> 
<script defer src="js/jquery-3.7.1.js"></script> 
<!-- Bootstrap JS + Popper JS --> 
<script defer src="js/bootstrap.min.js"></script>
</head>
<body>


 <div class="container-fluid"> 
 
<!-- Header --> 
<jsp:include page="/views/header.jsp" />
<!-- /Header -->

    <%-- ADD THIS BLOCK --%>
    <%
    java.util.List votes = (java.util.List) request.getAttribute("votes");
    if (votes == null) {
        out.println("<p>Список Голосований не установлен!</p>");
    } else if (votes.isEmpty()) {
        out.println("<p>Список Голосований пуст!</p>");
    } else {
        out.println("<p>Список Голосований содержит " + votes.size() + " элементов.</p>");
    }
    %>
    <%-- END OF ADDED BLOCK --%>

 <div class="container-fluid"> 
	<div class="row justify-content-start "> 
		<div class="col-8 border bg-light px-4"> 
			<h3>Список голосований</h3> 
			<table class="table"> 
				<thead>
					<tr>  
						<th scope="col">Тема голосования</th>
						<th scope="col">Дата начала голосования</th>
						<th scope="col">Дата окончания голосования</th>
						<th scope="col">Cтатус темы голосования</th> 
						<th scope="col">Редактировать</th> 
						<th scope="col">Удалить</th>
					</tr> 
				</thead> 
				<tbody> 
					<c:forEach var="vote" items="${votes}"> 
					<tr>
						<td>${vote.title}</td>
						<td>${vote.dateStart}</td>
						<td>${vote.dateFinish}</td>
						<td>${vote.status}</td> 
						<td width="20">
							<a href="editvote?id=${vote.id}" role="button" class="btn btn-outline-primary">
 							<img alt="Редактировать" src="images/icon-edit.png" width="25px" height="25px"></a>
						</td>
						<td width="20">
							<form action="${pageContext.request.contextPath}/deleteVote" method="post"style="display: inline;">
							<input type="hidden" name="voteId" value="${vote.id}">
								<button type="submit" class="btn btn-outline-danger"
										onclick="return confirm('Удалить голосование с кодом: ${vote.id}?')">  
									<img alt="Удалить" src="images/icon-delete.png" width="25px" height="25px">
								</button>
							</form>
						</td>
					</tr>
					</c:forEach>
				</tbody>
				</table>
		</div>
		<div class="col-4 border px-4">
			<form method="POST" action="${pageContext.request.contextPath}/vote">
				<h3>Новое голосование</h3>
				<div class="mb-3">
					<label for="votetitle" class="col-sm-3 col-form-label">Тема голосования:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="title" name="inputtitle" placeholder="Введите тему голосования">
					</div>
				</div>
				<div class="mb-3">
					<label for="votedatestart" class="col-sm-3 col-form-label">Дата начала голосования:</label>
					<div class="col-sm-4">
						<input type="date" class="form-control" id="dateStart" name="inputdateStart">
					</div>
				</div>
				<div class="mb-3">
					<label for="votedateFinish" class="col-sm-3 col-form-label">Дата окончания голосования:</label>
					<div class="col-sm-4">
						<input type="date" class="form-control" id="dateFinish" name="inputdateFinish">
					</div>
				</div>
            	<div class="mb-3">
					<label for="votestatus" class="col-sm-3 col-form-label">Статус темы голосования:</label>
					<div class="col-sm-8">
						<select class="form-control" id="status" name="inputstatus">
							<option value="">Выберите статус темы голосования</option>
							<option value="Активно">Активно</option>
							<option value="Завершено">Завершено</option>
						</select>
					</div>
				</div>
				<p>
				<c:if test="${not empty errorMessage}">
					<p style="color:red;">${errorMessage}</p>
				</c:if> 
				<br> <br> <br>
				<button type="submit" class="btn btn-primary">Добавить</button>
				</p>  
				<br> 
			</form>
		</div> 
	</div> 
</div>   
<footer>
	<jsp:include page="/views/footer.jsp"/>
</footer>
</body>
</html>
