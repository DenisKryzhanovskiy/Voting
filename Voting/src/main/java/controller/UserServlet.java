package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.ConnectionProperty;
import dao.UserDbDAO;
import domain.User;
import exception.DAOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L; 
	
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
        
		try {
	        new ConnectionProperty();
	        UserDbDAO usersDAO = new UserDbDAO();
	        List<User> users = usersDAO.findAll();
	        request.setAttribute("users", users);

	        System.out.println("Список User установлен в атрибут: " + (users != null ? users.size() : "null")); 

	    } catch (DAOException e) {
	        e.printStackTrace();
	        request.setAttribute("errorMessage", "Ошибка при получении списка Голосующих: " + e.getMessage());
	    }

	        request.getRequestDispatcher("/views/user.jsp").forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String firstName = request.getParameter("inputfirstName");
        String lastName = request.getParameter("inputlastName");
        String email  = request.getParameter("inputemail");
        String phone = request.getParameter("inputphone");
        String status = request.getParameter("inputstatus");
	        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty() || email  == null || email .isEmpty() || phone  == null || phone .isEmpty() || status == null || status.isEmpty()) {
	            request.setAttribute("errorMessage", "Пожалуйста, заполните все поля.");
	            doGet(request, response);  
	            return;
	        }
	        
	        User newUser = new User();
	        newUser.setFirstName(firstName);
	        newUser.setLastName(lastName);
	        newUser.setEmail(email);
	        newUser.setPhone(phone);
	        newUser.setStatus(status);

	        UserDbDAO userDAO = null;
	        try {
	             new ConnectionProperty();
	             userDAO = new UserDbDAO(); 
	             userDAO.insert(newUser);
	            System.out.println("User added successfully!");
	        } catch (DAOException e) {
	            e.printStackTrace();
	            request.setAttribute("errorMessage", "Ошибка при добавлении голосующего: " + e.getMessage());
	        } finally {
	            doGet(request, response);  
	        }
	}
	

}