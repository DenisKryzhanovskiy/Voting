package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ConnectionProperty;
import dao.QuestionDbDAO;
import domain.Question;
import dao.VoteDbDAO;
import domain.Vote;
import exception.DAOException;

/**
 * Servlet implementation class QuestionServlet
 */
@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L; 
	
    public QuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
        
		response.setContentType("text/html");
	    String userPath;
	    List<Question> que = null;
	    Map<String, Vote> voteMap = null; 
		
		try {
	        new ConnectionProperty();
	        QuestionDbDAO queDAO = new QuestionDbDAO();
	        VoteDbDAO voteDAO = new VoteDbDAO();
	        que = queDAO.findAll();
	        
	        List<Vote> allVotes = voteDAO.findAll();
	        voteMap = new HashMap<>();
	        for (Vote vote : allVotes) {
	        	voteMap.put(vote.getTitle(), vote);
	        }

	        request.setAttribute("que", que);
	        request.setAttribute("voteMap", voteMap); 

	        System.out.println("Список Question установлен в атрибут: " + (que != null ? que.size() : "null")); // ADD THIS LINE

	    } catch (DAOException e) {
	        e.printStackTrace();
	        request.setAttribute("errorMessage", "Ошибка при получении списка Question: " + e.getMessage());
	    }

	        request.getRequestDispatcher("/views/question.jsp").forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}