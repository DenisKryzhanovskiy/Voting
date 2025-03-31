package domain;

import java.time.LocalDate;

public class Vote {
    private Long id;
    private String title;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private String status;

    public Vote() {
    }

    public Vote(Long id, String title, LocalDate dateStart, LocalDate dateFinish, String status) {
        this.id = id;
        this.title = title;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
        
    public LocalDate getDateStart() {
    	return dateStart;
    }
    
    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }
   
    public void setDateFinish(LocalDate dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", status='" + status + '\'' +
                '}';
    }
}
