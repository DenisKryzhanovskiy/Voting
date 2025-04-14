package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.ChoiceDbDAO;
import domain.Choice;
import dao.QuestionDbDAO;
import domain.Question;
import dao.UserDbDAO;
import domain.User;
import exception.DAOException;

/**
 * Servlet implementation class QuestionServlet
 */
@WebServlet("/choice")
public class ChoiceServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L; 
	
    public ChoiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    private ChoiceDbDAO choiceDAO = new ChoiceDbDAO();
    private QuestionDbDAO questionDAO = new QuestionDbDAO();
    private UserDbDAO userDAO = new UserDbDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
        	List<Choice> choices = choiceDAO.findAll();
            List<Question> questions = questionDAO.findAll(); 
            List<User> users = userDAO.findAll();
            request.setAttribute("choices", choices); 
            request.setAttribute("questions", questions);
            request.setAttribute("users", users); 

        } catch (DAOException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка при получении списка Выбора голосующих: " + e.getMessage());
        }

        request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String questionIdStr = request.getParameter("questionId");
        String userIdStr = request.getParameter("userId");
        String choiceUser = request.getParameter("choiceUser");

        try {
            // 1. Validate Input
            if (questionIdStr == null || questionIdStr.isEmpty() || userIdStr == null || userIdStr.isEmpty() || choiceUser == null || choiceUser.isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Пожалуйста, заполните все поля.");
                response.sendRedirect(request.getContextPath() + "/choice"); 
                return;
            }

            Long questionId;
            Long userId;

            try {
                questionId = Long.parseLong(questionIdStr);
                userId = Long.parseLong(userIdStr);

                // **Important: Validate if question and user exist in the database**
                Question question = questionDAO.findById(questionId);
                User user = userDAO.findById(userId);

                if (question == null) {
                    request.setAttribute("errorMessage", "Invalid Question ID.");
                    request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
                    return;
                }

                if (user == null) {
                    request.setAttribute("errorMessage", "Invalid User ID.");
                    request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
                    return;
                }

            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid Question or User ID format.");
                request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
                return;
            } catch (DAOException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error during question/user validation: " + e.getMessage());
                request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
                return;
            }
            
            // 2. Create Question Object
            Choice newChoice = new Choice();
            newChoice.setQuestionId(questionId);
            newChoice.setUserId(userId);
            newChoice.setChoiceUser(choiceUser);

            // 3. Save Question to DB
            try {
                choiceDAO.insert(newChoice);

                // 4. Success - Redirect with message
                request.setAttribute("successMessage", "Choice added successfully!");
                response.sendRedirect(request.getContextPath() + "/choice"); // Redirect to choice list

            } catch (DAOException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error adding choice: " + e.getMessage());
                 request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
                return;
            }
        }
         catch (Exception e) {  // Catch any unexpected exception
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
        }
    }
}