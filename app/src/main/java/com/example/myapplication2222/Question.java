package com.example.myapplication2222;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsUsed;
    private  boolean mIsCheted;

    public Question(int textResId, boolean answerTrue, boolean isUsed, boolean isCheted) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mIsUsed = isUsed;
        mIsCheted = isCheted;
    }

    public int getTextResId(){
        return mTextResId;
    }

    public void setTextResId(int textResId){
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    public  boolean isUsed(){
        return mIsUsed;
    }

    public boolean isCheted() {
        return mIsCheted;
    }

    public void setCheated(boolean isCheted){
        mIsCheted = isCheted;
    }
    public void setUsed(boolean used){
        mIsUsed = used;
    }
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
