package com.example.demo.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exam")
public class Exam {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long examId;

    private String title;

    @Lob
    @Column( length = 1000000 )
    private String description;

    private long create_by;

    private Timestamp create_date;

    private Timestamp modify_date;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "exams" , fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<Question>();

    public Exam (){

    }

    public Exam(String title, String description, long create_by, Timestamp create_date, Timestamp modify_date) {
        this.title = title;
        this.description = description;
        this.create_by = create_by;
        this.create_date = create_date;
        this.modify_date = modify_date;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreate_by() {
        return create_by;
    }

    public void setCreate_by(long create_by) {
        this.create_by = create_by;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public Timestamp getModify_date() {
        return modify_date;
    }

    public void setModify_date(Timestamp modify_date) {
        this.modify_date = modify_date;
    }
}
