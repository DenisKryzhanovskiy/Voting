package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.ConnectionProperty;
import dao.ChoiceDbDAO;
import dao.QuestionDbDAO;
import dao.UserDbDAO;
import domain.Choice;
import domain.Question;
import domain.User;
import exception.DAOException;

@WebServlet("/choice")
public class ChoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ChoiceDbDAO choiceDAO = new ChoiceDbDAO();
    private final QuestionDbDAO questionDAO = new QuestionDbDAO();
    private final UserDbDAO userDAO = new UserDbDAO();

    public ChoiceServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            new ConnectionProperty();
            List<Choice> choices = choiceDAO.findAll();
            List<Question> questions = questionDAO.findAll();
            List<User> users = userDAO.findAll();

            request.setAttribute("choices", choices);
            request.setAttribute("questions", questions);
            request.setAttribute("users", users);

        } catch (DAOException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage",
                    "Ошибка при получении списка Выбора голосующих: " + e.getMessage());
        }

        request.getRequestDispatcher("/views/choice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String questionIdStr = request.getParameter("questionId");
        String userIdStr = request.getParameter("userId");
        String choiceUser = request.getParameter("choiceUser");

        if (questionIdStr == null || questionIdStr.isEmpty()
                || userIdStr == null || userIdStr.isEmpty()
                || choiceUser == null || choiceUser.isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Пожалуйста, заполните все поля.");
            response.sendRedirect(request.getContextPath() + "/choice");
            return;
        }

        Long questionId;
        Long userId;

        try {
            questionId = Long.parseLong(questionIdStr);
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage",
                    "Invalid Question or User ID format.");
            response.sendRedirect(request.getContextPath() + "/choice");
            return;
        }

        try {
            new ConnectionProperty();

            Question question = questionDAO.findById(questionId);
            User user = userDAO.findById(userId);

            if (question == null) {
                request.getSession().setAttribute("errorMessage", "Invalid Question ID.");
                response.sendRedirect(request.getContextPath() + "/choice");
                return;
            }

            if (user == null) {
                request.getSession().setAttribute("errorMessage", "Invalid User ID.");
                response.sendRedirect(request.getContextPath() + "/choice");
                return;
            }

            Choice newChoice = new Choice();
            newChoice.setQuestionId(questionId);
            newChoice.setUserId(userId);
            newChoice.setChoiceUser(choiceUser);

            choiceDAO.insert(newChoice);
            request.getSession().setAttribute("successMessage", "Choice added successfully!");

            response.sendRedirect(request.getContextPath() + "/choice");

        } catch (DAOException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage",
                    "Error adding choice: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/choice");
        }
    }
}