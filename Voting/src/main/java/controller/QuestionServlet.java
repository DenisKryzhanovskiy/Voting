package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.HashMap;
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
    private QuestionDbDAO questionDAO = new QuestionDbDAO();
    private VoteDbDAO voteDAO = new VoteDbDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            List<Question> questions = questionDAO.findAll(); 
             List<Vote> votes = voteDAO.findAll(); 
            request.setAttribute("questions", questions);
            request.setAttribute("votes", votes); 

        } catch (DAOException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка при получении списка Вопросов голосования: " + e.getMessage());
        }

        request.getRequestDispatcher("/views/question.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String voteIdStr = request.getParameter("voteId");
        String content = request.getParameter("content");
        String dateVoteStr = request.getParameter("dateVote");

        try {
            // 1. Validate Input
            if (voteIdStr == null || voteIdStr.isEmpty() || content == null || content.isEmpty() || dateVoteStr == null || dateVoteStr.isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Пожалуйста, заполните все поля.");
                response.sendRedirect(request.getContextPath() + "/question"); 
                return;
            }

            Long voteId;
            try {
                voteId = Long.parseLong(voteIdStr);
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", "Invalid Question ID format.");
                response.sendRedirect(request.getContextPath() + "/question");
                return;
            }

            LocalDate dateVote;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dateVote = LocalDate.parse(dateVoteStr, formatter);
            } catch (DateTimeParseException e) {
                request.getSession().setAttribute("errorMessage", "Неверный формат даты. Пожалуйста введите дату в следующем формате Год-Месяц-День.");
                response.sendRedirect(request.getContextPath() + "/question");
                return;
            }

            // 2. Create Question Object
            Question newQuestion = new Question();
            newQuestion.setVoteId(voteId);
            newQuestion.setContent(content);
            newQuestion.setDateVote(dateVote);

            // 3. Save Question to DB
            questionDAO.insert(newQuestion);

            // 4. Success - Redirect with message
            request.getSession().setAttribute("successMessage", "Question added successfully!");
            response.sendRedirect(request.getContextPath() + "/question"); // Redirect to question list

        } catch (DAOException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Error adding question: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/question");
        }
    }
}