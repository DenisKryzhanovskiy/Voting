<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%> 
<!DOCTYPE html> 
<html lang="ru"> 
<head>  
<meta charset="UTF-8"> 
<!-- Настройка viewport --> 
 <meta name="viewport" content="width=device-width, initial-scale=1"> 
<!-- Bootstrap CSS --> 
<link rel="stylesheet" href="css/bootstrap.min.css"> 
 
<title>Ведение голосования</title> 
 
</head> 
<body> 
 	 <div class="container-fluid"> 
	 <jsp:include page="/views/header.jsp" /> 
 	 <div class="container"> 
  		  <br><br><br> 
    		   <div class="list-group text-center py-3 px-3"> 
    				<h2>Функции системы</h2> 
    				<ul class="list-group list-group-flush"> 
     					 <li class="list-group-item list-group-item
primary"><a href="#">Голосование</a> 
     					 <li class="list-group-item list-group-item
info"><a href="#">Вопросы голосования</a>
						 <li class="list-group-item list-group-item
info"><a href="#">Голосующий</a> 
						 <li class="list-group-item list-group-item
info"><a href="#">Результат голосования</a>  
    				</ul> 
     		   </div> 
  			<br><br> 
 	  </div> 
	<jsp:include page="/views/footer.jsp" />   	  
</div> 
</body> 
</html> 