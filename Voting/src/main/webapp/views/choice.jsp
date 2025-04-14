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
    java.util.List choices = (java.util.List) request.getAttribute("choices");
    if (choices == null) {
        out.println("<p>Список Результатов Голосования не установлен!</p>");
    } else if (choices.isEmpty()) {
        out.println("<p>Список Результатов Голосования пуст!</p>");
    } else {
        out.println("<p>Список Результатов Голосования содержит " + choices.size() + " элементов.</p>");
    }
    %>
    <%-- END OF ADDED BLOCK --%>

 <div class="container-fluid"> 
      <div class="row justify-content-start "> 
        <div class="col-8 border bg-light px-4"> 
          <h3>Список результатов голосования</h3> 
          <table class="table"> 
            <thead> 
              <th scope="col">Код вопроса голосования</th>
              <th scope="col">Код голосующего</th>
              <th scope="col">Выбор голосующего</th>
              <th scope="col">Редактировать</th> 
              <th scope="col">Удалить</th> 
            </thead> 
            <tbody> 
              <c:forEach var="choice" items="${choices}"> 
                <tr>
				  <td>${choice.questionId}</td>
				  <td>${choice.userId}</td>
				  <td>${choice.choiceUser}</td>
                  <td width="20"><a href="#" role="button" 
                     class="btn btn-outline-primary">  
                     <img alt="Редактировать" 
                     src="images/icon-edit.png" width="25px" height="25px"></a></td> 
                   <td width="20"><a href="#" role="button" 
                     class="btn btn-outline-primary">  
                     <img alt="Удалить" 
                        src="images/icon-delete.png" width="25px" height="25px"></a></td> 
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
                  				<option value="${question.id}">${question.content}
                  				</option>
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
                  				<option value="${user.id}">${user.firstName} ${user.lastName}
                  				</option>
							 </c:forEach>
							</select>
						 </div>
					 </div> 
            <div class="mb-3"> 
              <br> <label for="inputchoiceUser" class="col-sm-3 col-form-label">Выбор голосующего</label> 
              <div class="col-sm-7"> 
                <input type="text" name="choiceUser" class="form-control" id="choiceUser" placeholder="Введите выбор голосующего"/> 
            </div> 
          </div>
          <p>
              <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if> 
            <br> <br> <br> 
<button type="submit"  
class="btn btn-primary">Добавить</button> 
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
