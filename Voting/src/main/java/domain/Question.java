package domain;

import java.time.LocalDate;

public class Question {
    private Long id;
    private Long voteId;
    private String content;
    private LocalDate dateVote;
    private Vote vote;

    public Question() {
    }

    public Question(Long id, Long voteId, String content, LocalDate dateVote) {
        this.id = id;
        this.voteId = voteId;
        this.content = content;
        this.dateVote = dateVote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDateVote() {
        return dateVote;
    }

    public void setDateVote(LocalDate dateVote) {
        this.dateVote = dateVote;
    }

    public Vote getVote() {   // ADD THIS METHOD: Getter for the Vote object
        return vote;
    }

    public void setVote(Vote vote) { // ADD THIS METHOD: Setter for the Vote object
        this.vote = vote;
    }
    
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", voteId=" + voteId +
                ", content='" + content + '\'' +
                ", dateVote=" + dateVote +
                ", vote=" + vote +
                '}';
    }
}