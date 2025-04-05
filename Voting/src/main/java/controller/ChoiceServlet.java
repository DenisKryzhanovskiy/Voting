package controller; 
import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet; 
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.util.List; 
import domain.Choice; 
import exception.DAOException; 
import dao.ChoiceDbDAO; 
import dao.ConnectionProperty; 
/** 
* Servlet implementation class ChoiceServlet 
*/ 
@WebServlet("/choice") 
public class ChoiceServlet extends HttpServlet { 
 
 private static final long serialVersionUID = 1L; 
 
 ConnectionProperty prop; 
 
 /** 
  * @throws IOException 
  * @throws FileNotFoundException 
  * @see HttpServlet#HttpServlet() 
  */ 
 public ChoiceServlet() throws FileNotFoundException, IOException { 
  super(); 
  // TODO Auto-generated constructor stub 
  prop = new ConnectionProperty(); 
 } 
 
 /** 
  * @see HttpServlet#doGet(HttpServletRequest request, 
HttpServletResponse 
  *      response) 
  */ 
 protected void doGet(HttpServletRequest request, 
HttpServletResponse response) 
   throws ServletException, IOException { 
 
  response.setContentType("text/html"); 
  String userPath; 
  List<Choice> choices; 
  ChoiceDbDAO dao = new ChoiceDbDAO(); 
 
  try { 
	  choices = dao.findAll(); 
   request.setAttribute("choices", choices); 
  } catch (DAOException e) { 
   // TODO Auto-generated catch block 
   e.printStackTrace(); 
  } 
  userPath = request.getServletPath(); 
  if ("/choice".equals(userPath)) { 
  
 request.getRequestDispatcher("/views/choice.jsp").forward(request, 
response); 
  } 
 } 
 
 /** 
  * @see HttpServlet#doPost(HttpServletRequest request, 
HttpServletResponse 
  *      response) 
  */ 
 protected void doPost(HttpServletRequest request, 
HttpServletResponse response) 
 
   throws ServletException, IOException { 
  // TODO Auto-generated method stub 
  doGet(request, response); 
 } 
} 