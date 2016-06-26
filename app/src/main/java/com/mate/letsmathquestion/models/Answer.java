package com.mate.letsmathquestion.models;

/**
 * Created by sasuke on 26/6/16.
 */
public class Answer {

    String answerID;
    String question_id;
    String answer;
    String user_id;

    public Answer() {
    }

    public Answer(String answerID, String question_id, String answer, String user_id) {
        this.answerID = answerID;
        this.question_id = question_id;
        this.answer = answer;
        this.user_id = user_id;
    }

    public Answer(String question_id, String answer, String user_id) {
        this.question_id = question_id;
        this.answer = answer;
        this.user_id = user_id;
    }

    public String getAnswerID() {
        return answerID;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
