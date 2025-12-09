package controller;

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

import dao.ConnectionProperty;
import dao.QuestionDbDAO;
import dao.VoteDbDAO;
import domain.Question;
import domain.Vote;
import exception.DAOException;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public QuestionServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            new ConnectionProperty();
            QuestionDbDAO questionDAO = new QuestionDbDAO();
            VoteDbDAO voteDAO = new VoteDbDAO();

            List<Question> questions = questionDAO.findAll();
            List<Vote> votes = voteDAO.findAll();

            System.out.println("QuestionServlet.doGet: questions=" +
                    (questions != null ? questions.size() : "null") +
                    ", votes=" + (votes != null ? votes.size() : "null"));

            request.setAttribute("questions", questions);
            request.setAttribute("votes", votes);
        } catch (DAOException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage",
                    "Ошибка при получении списка Вопросов голосования: " + e.getMessage());
        }

        request.getRequestDispatcher("/views/question.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String voteIdStr = request.getParameter("voteId");
        String content = request.getParameter("content");
        String dateVoteStr = request.getParameter("dateVote");

        if (voteIdStr == null || voteIdStr.isEmpty()
                || content == null || content.isEmpty()
                || dateVoteStr == null || dateVoteStr.isEmpty()) {
            request.setAttribute("errorMessage", "Пожалуйста, заполните все поля.");
            doGet(request, response);
            return;
        }

        Long voteId;
        try {
            voteId = Long.parseLong(voteIdStr.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Неверный формат ID голосования.");
            doGet(request, response);
            return;
        }

        LocalDate dateVote;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateVote = LocalDate.parse(dateVoteStr, formatter);
        } catch (DateTimeParseException e) {
            request.setAttribute("errorMessage",
                    "Неверный формат даты. Пожалуйста, введите дату в формате Год-Месяц-День.");
            doGet(request, response);
            return;
        }

        try {
            new ConnectionProperty();
            QuestionDbDAO questionDAO = new QuestionDbDAO();

            Question newQuestion = new Question();
            newQuestion.setVoteId(voteId);
            newQuestion.setContent(content);
            newQuestion.setDateVote(dateVote);

            questionDAO.insert(newQuestion);
            System.out.println("Вопрос успешно добавлен для голосования ID: " + voteId);
        } catch (DAOException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка при добавлении вопроса: " + e.getMessage());
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/question");
    }
}