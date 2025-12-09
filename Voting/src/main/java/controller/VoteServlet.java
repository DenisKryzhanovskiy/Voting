package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import dao.ConnectionProperty;
import dao.VoteDbDAO;
import domain.Vote;
import exception.DAOException;


@WebServlet("/vote")
public class VoteServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L; 
	
    public VoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
        
		try {
	        new ConnectionProperty();
	        VoteDbDAO votesDAO = new VoteDbDAO();
	        List<Vote> votes = votesDAO.findAll();
	        request.setAttribute("votes", votes);

	        System.out.println("Список Vote установлен в атрибут: " + (votes != null ? votes.size() : "null")); 

	    } catch (DAOException e) {
	        e.printStackTrace();
	        request.setAttribute("errorMessage", "Ошибка при получении списка Голосования: " + e.getMessage());
	    }

	        request.getRequestDispatcher("/views/vote.jsp").forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String title = request.getParameter("inputtitle");
        String dateStartStr = request.getParameter("inputdateStart");
        String dateFinishStr  = request.getParameter("inputdateFinish");
        String status = request.getParameter("inputstatus");
	        if (title == null || title.isEmpty() || dateStartStr == null || dateStartStr.isEmpty() || dateFinishStr  == null || dateFinishStr .isEmpty() || status == null || status.isEmpty()) {
	            request.setAttribute("errorMessage", "Пожалуйста, заполните все поля.");
	            doGet(request, response); 
	            return;
	        }

	        LocalDate dateStart = null;
	        LocalDate dateFinish = null;

	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            dateStart = LocalDate.parse(dateStartStr, formatter);
	            dateFinish = LocalDate.parse(dateFinishStr, formatter);
	        } catch (DateTimeParseException e) {
	            request.setAttribute("errorMessage", "Неверный формат даты. Пожалуйста, введите дату в формате Год-Месяц-День.");
	            doGet(request, response);
	            return;
	        }

	        if (dateStart != null && dateFinish != null && dateStart.isAfter(dateFinish)) {
	            request.setAttribute("errorMessage", "Дата начала голосования не может быть после Даты окончания голосования.");
	            doGet(request, response);
	            return;
	        }
	        
	        Vote newVote = new Vote();
	        newVote.setTitle(title);
	        newVote.setDateStart(dateStart);
	        newVote.setDateFinish(dateFinish);
	        newVote.setStatus(status);

	        VoteDbDAO voteDAO = null; 
	        try {
	             new ConnectionProperty();
	             voteDAO = new VoteDbDAO();
	             voteDAO.insert(newVote);
	            System.out.println("Vote added successfully!");
	        } catch (DAOException e) {
	            e.printStackTrace();
	            request.setAttribute("errorMessage", "Ошибка при добавлении голосования: " + e.getMessage());
	        } finally {
	            doGet(request, response); 
	        }
	}
	

}