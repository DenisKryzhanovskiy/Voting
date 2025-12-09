<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
<%@ page import="domain.Choice"%>
<%@ page import="domain.Question" %>
<%@ page import="domain.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Результат голосования</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"> 
<script defer src="${pageContext.request.contextPath}/js/jquery-3.7.1.js"></script> 
<script defer src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid"> 
 
<jsp:include page="/views/header.jsp" />

<%
    java.util.List choices = (java.util.List) request.getAttribute("choices");
    if (choices == null) {
        out.println("<p>Список Результатов Голосования не установлен!</p>");
    } else if (choices.isEmpty()) {
        out.println("<p>Список Результатов Голосования пуст!</p>");
    } else {
        out.println("<p>Список Результатов Голосования содержит " + choices.size() + " элементов.</p>");
    }
%>

<div class="container-fluid"> 
<div class="row justify-content-start "> 
<div class="col-8 border bg-light px-4"> 
<h3>Список результатов голосования</h3>

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
    <th scope="col">Код вопроса голосования</th>
    <th scope="col">Код голосующего</th>
    <th scope="col">Выбор голосующего</th>
    <th scope="col">Редактировать</th> 
    <th scope="col">Удалить</th>
</tr> 
</thead> 
<tbody> 
<c:forEach var="choice" items="${choices}"> 
<tr>
    <td>${choice.id}</td>
    <td>${choice.questionId}</td>
    <td>${choice.userId}</td>
    <td>${choice.choiceUser}</td>

    <td width="20">
        <a href="${pageContext.request.contextPath}/editchoice?choiceId=${choice.id}"
           role="button" class="btn btn-outline-primary">
            <img alt="Редактировать" src="images/icon-edit.png" width="25px" height="25px">
        </a>
    </td> 

    <td width="20">
        <a href="${pageContext.request.contextPath}/deletechoice?choiceId=${choice.id}"
           role="button" class="btn btn-outline-danger"
           onclick="return confirm('Удалить выбор #${choice.id}?');">
            <img alt="Удалить" src="images/icon-delete.png" width="25px" height="25px">
        </a>
    </td> 
</tr> 
</c:forEach> 
</tbody> 
</table> 
</div> 

<div class="col-4 border px-4"> 
<form method="POST" action="${pageContext.request.contextPath}/choice"> 
<h3>Новый результат голосования</h3>

<div class="mb-3">
    <label for="questionId" class="col-sm-3 col-form-label">Код вопроса голосования</label>
    <div class="col-sm-7">
        <select name="questionId" class="form-control" required>
            <option value="">Выберете вопрос голосования</option>
            <c:forEach var="question" items="${questions}">
                <option value="${question.id}">${question.content}</option>
            </c:forEach>
        </select>
    </div>
</div> 

<div class="mb-3">
    <label for="userId" class="col-sm-3 col-form-label">Код голосующего</label>
    <div class="col-sm-7">
        <select name="userId" class="form-control" required>
            <option value="">Выберите голосующего</option>
            <c:forEach var="user" items="${users}">
                <option value="${user.id}">${user.firstName} ${user.lastName}</option>
            </c:forEach>
        </select>
    </div>
</div> 

<div class="mb-3"> 
    <br> 
    <label for="choiceUser" class="col-sm-3 col-form-label">Выбор голосующего</label> 
    <div class="col-sm-7"> 
        <input type="text" name="choiceUser" class="form-control" id="choiceUser"
               placeholder="Введите выбор голосующего"/> 
    </div> 
</div>

<c:if test="${not empty errorMessage}">
    <p style="color:red;">${errorMessage}</p>
</c:if> 

<br><br><br> 
<button type="submit" class="btn btn-primary">Добавить</button> 

<br> 
</form> 
</div> 
</div> 
</div>

<footer>
    <jsp:include page="/views/footer.jsp"/>
</footer>

</div>
</body>
</html>