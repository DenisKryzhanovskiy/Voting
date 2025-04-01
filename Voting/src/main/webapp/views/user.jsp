<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%> 
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
<title>Голосующий</title>
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
              <c:forEach var="user" items="${users}"> 
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