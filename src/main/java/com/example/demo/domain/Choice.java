package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "choice")
public class Choice {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    @Column( length = 1000000 )
    private String html;

    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId")
    private Question questions;

    public Choice(){

    }

    public Choice(String html, boolean isCorrect) {
        this.html = html;
        this.isCorrect = isCorrect;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Question getQuestions() {
        return questions;
    }

    public void setQuestions(Question questions) {
        this.questions = questions;
    }
}
