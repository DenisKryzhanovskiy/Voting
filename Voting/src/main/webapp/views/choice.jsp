<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
<%@ page import="domain.Choice"%>
<%

Choice c1 = new Choice(1L, 1L, 1L, "Кандидат A");
Choice c2 = new Choice(2L, 1L, 2L, "Кандидат B");
Choice c3 = new Choice(3L, 2L, 1L, "Лидерство");
Choice c4 = new Choice(4L, 2L, 2L, "Опыт");
Choice c5 = new Choice(3L, 3L, 1L, "Логотип 1");
Choice c6 = new Choice(4L, 3L, 4L, "Логотип 2");
Choice c7 = new Choice(3L, 4L, 1L, "Да");
Choice c8 = new Choice(4L, 4L, 4L, "Нет");
Choice[] choice = new Choice[]{c1, c2, c3, c4, c5, c6, c7, c8}; 
pageContext.setAttribute("choices", choice); 
%> 

<%@ page import="domain.Question"%>
<%@ page import="java.time.LocalDate" %>
<%

Question q1 = new Question(1L, 1L, "Кого вы хотите видеть президентом компании?", LocalDate.parse("2023-10-27"));
Question q2 = new Question(2L, 1L, "Какие качества важны в президенте компании?", LocalDate.parse("2023-10-27"));
Question q3 = new Question(3L, 2L, "Какой логотип лучше отражает ценности компании?", LocalDate.parse("2023-11-02"));
Question q4 = new Question(4L, 3L, "Устраивают ли вас текущие условия труда?", LocalDate.parse("2023-11-16"));
Question[] question = new Question[]{q1, q2, q3, q4}; 
pageContext.setAttribute("questions", question); 
%> 

<%@ page import="domain.User"%>
<%

User u1 = new User(1L, "Иван", "Иванов", "ivanov@example.com", "+79001234567", "Проголосовал");
User u2 = new User(2L, "Петр", "Петров", "petrov@example.com", "+79007654321", "Проголосовал");
User u3 = new User(3L, "Сидор", "Сидоров", "sidorov@example.com", "+79001122333", "Не проголосовал");
User u4 = new User(4L, "Анна", "Смирнова", "smirnova@example.com", "+79004455666", "Проголосовал");
User[] user = new User[]{u1, u2, u3, u4}; 
pageContext.setAttribute("users", user); 
%> 

<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script defer src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script defer src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<head>
<title>Результат голосования</title>
</head>
<body>
<header>
		<jsp:include page="/views/header.jsp"/>
	</header>
	<main>
 <div class="row justify-content-start "> 
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
          <form method="POST" action=""> 
            <h3>Новый результат голосования</h3>
            <div class="mb-3">
             <label for="questionId" class="col-sm-3 col-form-label">Код вопроса голосования</label>
						 <div class="col-sm-6">
						 	<select name="role" class="form-control">
							 <option>Выберите вопрос голосования</option>
								 <c:forEach var="question" items="${questions}">
									 <option value="${question}">
									 	<c:out value="${question.getContent()}"></c:out>
									 </option>
								 </c:forEach>
							 </select>

						 </div>
					 </div> 
            <div class="mb-3">
             <label for="userId" class="col-sm-3 col-form-label">Код голосующего</label>
						 <div class="col-sm-6">
						 	<select name="role" class="form-control">
							 <option>Выберите голосующего</option>
								 <c:forEach var="user" items="${users}">
									 <option value="${user}">
									 	<c:out value="${user.getFirstName()} ${user.getLastName()}"></c:out>
									 </option>
								 </c:forEach>
							 </select>

						 </div>
					 </div> 
            <div class="mb-3"> 
              <br> <label for="inputchoiceUser"  
              class="col-sm-3 col-form-label">Выбор голосующего</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputchoiceUser"  
                  class="form-control" id="inputchoiceUser" /> 
            </div> 
          </div>
          <p> 
            <br> <br> <br> 
<button type="submit"  
class="btn btn-primary">Добавить</button> 
<br> 
</p> 
</form> 
</div> 
</div> 
</div>
 </main> 
<footer>
		<jsp:include page="/views/footer.jsp"/>
	</footer>
</body>
</html>