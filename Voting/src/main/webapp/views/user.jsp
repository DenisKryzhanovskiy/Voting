<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
<%@ page import="domain.User"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Голосующий</title>

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
    java.util.List users = (java.util.List) request.getAttribute("users");
    if (users == null) {
        out.println("<p>Список Голосующих не установлен!</p>");
    } else if (users.isEmpty()) {
        out.println("<p>Список Голосующих пуст!</p>");
    } else {
        out.println("<p>Список Голосующих содержит " + users.size() + " элементов.</p>");
    }
    %>
    <%-- END OF ADDED BLOCK --%>

<div class="container-fluid"> 
<div class="row justify-content-start "> 
<div class="col-8 border bg-light px-4"> 
<h3>Список голосующих</h3>
          
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
<th scope="col">Имя</th>
<th scope="col">Фамилия</th>
<th scope="col">Почта</th>
<th scope="col">Телефон</th>
<th scope="col">Статус</th> 
<th scope="col">Редактировать</th> 
<th scope="col">Удалить</th>
</tr> 
</thead>
<tbody> 
<c:forEach var="user" items="${users}"> 
<tr>
<td>${user.firstName}</td>
<td>${user.lastName}</td>
<td>${user.email}</td>
<td>${user.phone}</td>
<td>${user.status}</td> 
<td width="20">
<a href="edituser?id=${user.id}" role="button" class="btn btn-outline-primary">  
<img alt="Редактировать" src="images/icon-edit.png" width="25px" height="25px"></a>
</td>
<td width="20">
    <form action="${pageContext.request.contextPath}/deleteuser" method="post" style="display: inline;">
        <input type="hidden" name="userId" value="${user.id}">
        <button type="submit" class="btn btn-outline-danger"
                onclick="return confirm('Удалить голосующего с кодом: ${user.id}?')">
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
<form method="POST" action="${pageContext.request.contextPath}/user"> 
<h3>Новый голосующий</h3> 
<div class="mb-3"> 
<br> <label for="inputfirstName" class="col-sm-3 col-form-label">Имя</label> 
<div class="col-sm-6"> 
<input type="text" name="inputfirstName" class="form-control" id="userfirstName" required placeholder="Введите имя"/> 
</div> 
</div>
<div class="mb-3"> 
<br> <label for="inputlastName" class="col-sm-3 col-form-label">Фамилия</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputlastName" class="form-control" id="userlastName" required placeholder="Введите фамилию"/> 
</div> 
</div>
<div class="mb-3"> 
<br> <label for="inputemail" class="col-sm-3 col-form-label">Почта</label> 
<div class="col-sm-6"> 
<input type="email" name="inputemail" class="form-control" id="useremail" placeholder="pochta@mail.ru"/> 
</div>
</div>
<div class="mb-3"> 
<br> <label for="inputphone" class="col-sm-3 col-form-label">Телефон</label> 
 <div class="col-sm-6"> 
<input type="text" name="inputphone" class="form-control" id="userphone" placeholder="+79999999999"/> 
</div>  
</div>
<div class="mb-3"> 
<br> <label for="inputstatus" class="col-sm-3 col-form-label">Статус</label> 
<div class="col-sm-6"> 
<select class="form-control" id="userstatus" name="inputstatus">
<option value="">Выберите статус голосующего</option>
<option value="Проголосовал">Проголосовал</option>
<option value="Не проголосовал">Не проголосовал</option>
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