package domain;

public class Choice {
    private Long id;
    private Long questionId;
    private Long userId;
    private String choiceUser;

    public Choice() {
    }

    public Choice(Long id, Long questionId, Long userId, String choiceUser) {
        this.id = id;
        this.questionId = questionId;
        this.userId = userId;
        this.choiceUser = choiceUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getChoiceUser() {
        return choiceUser;
    }

    public void setChoiceUser(String choiceUser) {
        this.choiceUser = choiceUser;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", choiceUser='" + choiceUser + '\'' +
                '}';
    }
}