<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
<%@ page import="domain.Question"%>

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

    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>

    <%-- ADD THIS BLOCK --%>
    <%
    java.util.List que = (java.util.List) request.getAttribute("que");
    if (que == null) {
        out.println("<p>Список Question не установлен!</p>");
    } else if (que.isEmpty()) {
        out.println("<p>Список Question пуст!</p>");
    } else {
        out.println("<p>Список Question содержит " + que.size() + " элементов.</p>");
    }
    %>
    <%-- END OF ADDED BLOCK --%>

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
              <c:forEach var="question" items="${que}">
              	<c:set var="vote" value="${voteMap[question.voteId]}" /> 
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
