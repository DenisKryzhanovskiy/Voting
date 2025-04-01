<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
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
<title>Голосование</title>
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
          <h3>Список голосований</h3> 
          <table class="table"> 
            <thead>  
              <th scope="col">Тема голосования</th>
              <th scope="col">Дата начала голосования</th>
              <th scope="col">Дата окончания голосования</th>
              <th scope="col">Cтатус темы голосования</th> 
              <th scope="col">Редактировать</th> 
              <th scope="col">Удалить</th> 
            </thead> 
            <tbody> 
              <c:forEach var="vote" items="${votes}"> 
                <tr>
				  <td>${vote.title}</td>
				  <td>${vote.dateStart}</td>
				  <td>${vote.dateFinish}</td>
				  <td>${vote.status}</td> 
                  <td width="5" height="5"><a href="#" role="button" 
                     class="btn btn-outline-primary">  
                     <img alt="Редактировать" 
                     src="images/icon-edit.png" width="25px" height="25px"></a></td> 
                   <td width="5" height="5"><a href="#" role="button" 
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
            <h3>Новое голосование</h3> 
            <div class="mb-3"> 
              <br> <label for="inputtitle"  
              class="col-sm-3 col-form-label">Тема голосования</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputtitle"  
                  class="form-control" id="inputtitle" /> 
            </div>
            <div class="mb-3"> 
              <br> <label for="inputdateStart"  
              class="col-sm-3 col-form-label">Дата начала голосования</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputdateStart"  
                  class="form-control" id="inputdateStart" /> 
            </div>
            <div class="mb-3"> 
              <br> <label for="inputdateFinish"  
              class="col-sm-3 col-form-label">Дата окончания голосования</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputdateFinish"  
                  class="form-control" id="inputdateFinish" /> 
            </div>
            <div class="mb-3"> 
              <br> <label for="inputstatus"  
              class="col-sm-3 col-form-label">Cтатус темы голосования</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputstatus"  
                  class="form-control" id="inputstatus" /> 
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