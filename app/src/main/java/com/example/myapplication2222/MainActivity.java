package com.example.myapplication2222;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button mNextButton;
    private  Button mCheatButton;
    private int countTrueAns = 0;
    private int countFalseAns = 0;
    private TextView mQuestionTextView;
    private static final String KEY_INDEX = "index";
    private static final String KEY_IS_CHEATED = "index_IsCheated";
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.main_question,true,false,false),
            new Question(R.string.question_Canberra,true,false,false),
            new Question(R.string.question_africa, false,false,false),
            new Question(R.string.question_americas,true,false,false),
            new Question(R.string.question_asia,true,false,false),
            new Question(R.string.question_mideast,false,false,false),
            new Question(R.string.question_oceans,true,false,false)
    };

    private static final String TAG = "MainActivity";

    private int mCurrentIndex = 0;
    private int mPrevIndex = 0;
    private static  boolean mIsCheater;
    public static int countAnsShown = 0;
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheated(mIsCheater);
            if(mIsCheater) countAnsShown++;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState !=null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATED);
        }
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mQuestionBank[mCurrentIndex].setUsed(true);
                   checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mQuestionBank[mCurrentIndex].setUsed(true);
                checkAnswer(false);
            }
        });
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int varPrev = mPrevIndex;
                mPrevIndex = mCurrentIndex;
                mCurrentIndex = varPrev;
                updateQuestion();
            }
        });

        updateQuestion();

        mNextButton = (Button) findViewById((R.id.next_button));
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrevIndex = mCurrentIndex;
                mCurrentIndex = (mCurrentIndex+1)% mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });
        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIstrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIstrue);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });
        updateQuestion();
        }

        public void updateQuestion(){
        Log.d(TAG, "Updating question text ", new Exception());
            if(mQuestionBank[mCurrentIndex].isUsed()){
                mFalseButton.setEnabled(false);
                mTrueButton.setEnabled(false);
            }
            else{
                mFalseButton.setEnabled(true);
                mTrueButton.setEnabled(true);
            }
            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
    }
    private void checkAnswer( boolean userPressedTrue){
        boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if(mIsCheater || mQuestionBank[mCurrentIndex].isCheted()){
            messageResId = R.string.judgment_toast;
        }
        else {
            if (!mQuestionBank[mCurrentIndex].isCheted() &&  userPressedTrue == isAnswerTrue) {
                countTrueAns++;
                messageResId = R.string.correct_toast;
                mPrevIndex = mCurrentIndex;
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            } else if (!mQuestionBank[mCurrentIndex].isCheted()){
                messageResId = R.string.incorrect_toast;
                countFalseAns++;
                mPrevIndex = mCurrentIndex;
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            }
        }
        if ((countTrueAns + countFalseAns) < mQuestionBank.length) {
                Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.show();
        }
        else {
                String ans = "You answered right " + countTrueAns + " from " + mQuestionBank.length + ".";
                Toast toast = Toast.makeText(this, ans, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.show();
        }
        updateQuestion();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() is called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() is called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() is called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() is called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG,"onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_IS_CHEATED,mIsCheater);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }
}