<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
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

<%@ page import="domain.Vote"%>
<%@ page import="java.time.LocalDate" %>
<%

Vote r1 = new Vote(1L, "Выборы президента компании", LocalDate.parse("2023-10-26"), LocalDate.parse("2023-11-05"), "Активно");
Vote r2 = new Vote(2L, "Выбор нового логотипа", LocalDate.parse("2023-11-01"), LocalDate.parse("2023-11-10"), "Активно");
Vote r3 = new Vote(3L, "Опрос об условиях труда", LocalDate.parse("2023-11-15"), LocalDate.parse("2023-11-22"), "Завершено");
Vote[] vote = new Vote[]{r1, r2, r3}; 
pageContext.setAttribute("votes", vote); 
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
<title>Вопросы голосования</title>
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
          <h3>Список вопросов голосования</h3> 
          <table class="table"> 
            <thead> 
              <th scope="col">Код голосования</th>
              <th scope="col">Cодержание вопроса</th>
              <th scope="col">Дата голосования</th>
              <th scope="col">Редактировать</th> 
              <th scope="col">Удалить</th> 
            </thead> 
            <tbody> 
              <c:forEach var="question" items="${questions}"> 
                <tr>
				  <td>${question.voteId}</td>
				  <td>${question.content}</td>
				  <td>${question.dateVote}</td>
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
            <h3>Новый вопрос голосования</h3>
            <div class="mb-3">
             <label for="questionvoteId" class="col-sm-3 col-form-label">Код голосования</label>
						 <div class="col-sm-6">
						 	<select name="role" class="form-control">
							 <option>Выберите тему голосования</option>
								 <c:forEach var="vote" items="${votes}">
									 <option value="${vote}">
									 	<c:out value="${vote.getTitle()}"></c:out>
									 </option>
								 </c:forEach>
							 </select>
						 </div>
					 </div>  
            <div class="mb-3"> 
              <br> <label for="inputcontent"  
              class="col-sm-3 col-form-label">Cодержание вопроса</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputcontent"  
                  class="form-control" id="inputcontent" /> 
            </div> 
          </div>
          <div class="mb-3"> 
              <br> <label for="inputdateVote"  
              class="col-sm-3 col-form-label">Дата голосования</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputdateVote"  
                  class="form-control" id="inputdateVote" /> 
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