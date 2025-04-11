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
import dao.ChoiceDbDAO;
import domain.Choice;
import exception.DAOException;

/**
 * Servlet implementation class ChoiceServlet
 */
@WebServlet("/choice")
public class ChoiceServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L; 
	
    public ChoiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
        
		try {
	        new ConnectionProperty();
	        ChoiceDbDAO choDAO = new ChoiceDbDAO();
	        List<Choice> cho = choDAO.findAll();
	        request.setAttribute("cho", cho);

	        System.out.println("Список Choice установлен в атрибут: " + (cho != null ? cho.size() : "null")); // ADD THIS LINE

	    } catch (DAOException e) {
	        e.printStackTrace();
	        request.setAttribute("errorMessage", "Ошибка при получении списка Choice: " + e.getMessage());
	    }

	        request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}