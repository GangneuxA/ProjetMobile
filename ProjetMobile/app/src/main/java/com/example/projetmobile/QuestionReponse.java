package com.example.projetmobile;

public class QuestionReponse {

    private int id;
    private String question;
    private String reponse;

    public QuestionReponse(int id, String question, String reponse) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "QuestionReponse{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", reponse='" + reponse + '\'' +
                '}';
    }
}
