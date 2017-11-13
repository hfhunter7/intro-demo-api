package com.example.demo.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "question")
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long questionId;

    @Lob
    @Column( length = 1000000 )
    private String html;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exams;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "questions" , fetch = FetchType.LAZY)
    private Set<Choice> choices = new HashSet<Choice>();

    public Question (){

    }

    public Exam getExams() {
        return exams;
    }

    public void setExams(Exam exams) {
        this.exams = exams;
    }

    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = choices;
    }

    public Question(String html) {
        this.html = html;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
