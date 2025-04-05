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

    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>

    <%-- ADD THIS BLOCK --%>
    <%
    java.util.List pro = (java.util.List) request.getAttribute("pro");
    if (pro == null) {
        out.println("<p>Список Vote не установлен!</p>");
    } else if (pro.isEmpty()) {
        out.println("<p>Список Vote пуст!</p>");
    } else {
        out.println("<p>Список Vote содержит " + pro.size() + " элементов.</p>");
    }
    %>
    <%-- END OF ADDED BLOCK --%>

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
              <c:forEach var="vote" items="${pro}"> 
                <tr>
				  <td>${vote.gettitle}</td>
				  <td>${vote.getdateStart}</td>
				  <td>${vote.getdateFinish}</td>
				  <td>${vote.getstatus}</td> 
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
