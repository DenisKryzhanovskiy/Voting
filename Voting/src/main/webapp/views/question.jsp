<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page import="java.util.List" %>
<%@ page import="domain.Question" %>
<%@ page import="domain.Vote" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Вопросы голосования</title>

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

    <%-- DEBUG BLOCK --%>
    <%
    java.util.List questions = (java.util.List) request.getAttribute("questions");
    java.util.List votes = (java.util.List) request.getAttribute("votes");
    if (questions == null) {
        out.println("<p style='color:red;'>Список Вопросов не установлен!</p>");
    } else if (questions.isEmpty()) {
        out.println("<p>Список Вопросов пуст!</p>");
    } else {
        out.println("<p>Список Вопросов содержит " + questions.size() + " элементов.</p>");
    }
    %>
    <%-- END DEBUG --%>

<div class="container-fluid">
<div class="row justify-content-start">
<div class="col-8 border bg-light px-4">
<h3>Список вопросов голосования</h3>

<c:if test="${not empty sessionScope.errorMessage}">
<p style="color:red;">${sessionScope.errorMessage}</p>
<% session.removeAttribute("errorMessage"); %>
</c:if>
<c:if test="${not empty sessionScope.successMessage}">
<p style="color:green;">${sessionScope.successMessage}</p>
<% session.removeAttribute("successMessage"); %>
</c:if>

<table class="table">
<thead>
<tr>
<th scope="col">ID</th>
<th scope="col">Код голосования</th>
<th scope="col">Содержание вопроса</th>
<th scope="col">Дата голосования</th>
<th scope="col">Редактировать</th>
<th scope="col">Удалить</th>
</tr>
</thead>
<tbody>
<c:forEach var="question" items="${questions}">
<tr>
<td>${question.id}</td>
<td>${question.voteId}</td>
<td>${question.content}</td>
<td>
<c:if test="${question.dateVote != null}">${question.dateVote}</c:if>
<c:if test="${question.dateVote == null}">Не указана</c:if>
</td>
<td width="20">
<a href="editquestion?questionId=${question.id}" role="button" class="btn btn-outline-primary">
<img alt="Редактировать" src="images/icon-edit.png" width="25px" height="25px"></a>
</td>
<td width="20">
    <form action="${pageContext.request.contextPath}/deletequestion" method="post" style="display: inline;">
        <input type="hidden" name="questionId" value="${question.id}">
        <button type="submit" class="btn btn-outline-danger btn-sm"
                onclick="return confirm('Удалить вопрос #${question.id}?\nГолосование: ${question.voteId}\n${question.content}')">
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
<form method="POST" action="${pageContext.request.contextPath}/question">
<h3>Новый вопрос голосования</h3>
<div class="mb-3">
<label for="questionvoteId" class="col-sm-3 col-form-label">Код голосования</label>
<div class="col-sm-7">
<select name="voteId" class="form-control" id="questionvoteId" required>
<option value="">Выберите тему голосования</option>
<c:forEach var="vote" items="${votes}">
<option value="${vote.id}">${vote.title}</option>
</c:forEach>
</select>
</div>
</div>
<div class="mb-3">
<label for="inputcontent" class="col-sm-3 col-form-label">Содержание вопроса</label>
<div class="col-sm-7">
<input type="text" name="content" class="form-control" id="questioncontent" placeholder="Введите вопрос голосования" required/>
</div>
</div>
<div class="mb-3">
<label for="inputdateVote" class="col-sm-3 col-form-label">Дата голосования</label>
<div class="col-sm-4">
<input type="date" name="dateVote" class="form-control" id="questiondateVote" required/>
</div>
</div>
<p> 
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