package domain;

public class Choice {
    private Long id;
    private Long questionId;
    private Long userId;
    private String choiceUser;
    private Vote vote;
    private User user;

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
    
    public Vote getVote() { 
        return vote;
    }

    public void setVote(Vote vote) { 
        this.vote = vote;
    }
    
    public Vote getUser() { 
        return vote;
    }

    public void setUser(User user) { 
        this.user = user;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", choiceUser='" + choiceUser + '\'' +
                ", vote=" + vote +
                ", user=" + user +
                '}';
    }
}