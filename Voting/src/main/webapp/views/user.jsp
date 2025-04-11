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

    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>

    <%-- ADD THIS BLOCK --%>
    <%
    java.util.List us = (java.util.List) request.getAttribute("us");
    if (us == null) {
        out.println("<p>Список User не установлен!</p>");
    } else if (us.isEmpty()) {
        out.println("<p>Список User пуст!</p>");
    } else {
        out.println("<p>Список User содержит " + us.size() + " элементов.</p>");
    }
    %>
    <%-- END OF ADDED BLOCK --%>

  <div class="container-fluid"> 
      <div class="row justify-content-start "> 
        <div class="col-8 border bg-light px-4"> 
          <h3>Список голосующих</h3> 
          <table class="table"> 
            <thead> 
              <th scope="col">Имя</th>
              <th scope="col">Фамилия</th>
              <th scope="col">Почта</th>
              <th scope="col">Телефон</th>
              <th scope="col">Статус</th> 
              <th scope="col">Редактировать</th> 
              <th scope="col">Удалить</th> 
            </thead>
            <tbody> 
              <c:forEach var="user" items="${us}"> 
                <tr>
				  <td>${user.firstName}</td>
				  <td>${user.lastName}</td>
				  <td>${user.email}</td>
				  <td>${user.phone}</td>
				  <td>${user.status}</td> 
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
            <h3>Новый голосующий</h3> 
            <div class="mb-3"> 
              <br> <label for="inputfirstName"  
              class="col-sm-3 col-form-label">Имя</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputfirstName"  
                  class="form-control" id="inputfirstName" /> 
            </div> 
          </div>
          <div class="mb-3"> 
              <br> <label for="inputlastName"  
              class="col-sm-3 col-form-label">Фамилия</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputlastName"  
                  class="form-control" id="inputlastName" /> 
            </div> 
          </div>
           <div class="mb-3"> 
              <br> <label for="inputemail"  
              class="col-sm-3 col-form-label">Почта</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inputemail"  
                  class="form-control" id="inputemail" /> 
            </div>
            <div class="mb-3"> 
              <br> <label for="inpuphone"  
              class="col-sm-3 col-form-label">Телефон</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inpuphone"  
                  class="form-control" id="inpuphone" /> 
            </div>  
          </div>
          <div class="mb-3"> 
              <br> <label for="inpustatus"  
              class="col-sm-3 col-form-label">Статус</label> 
              <div class="col-sm-6"> 
                <input type="text" name="inpustatus"  
                  class="form-control" id="inpustatus" /> 
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