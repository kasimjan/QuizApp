package com.example.myapplication2222;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.security.PrivateKey;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bgnerdrunch.android.geoquiz.answer_is_true";
    private static  final  String EXTRA_ANSWER_SHOWN = "com.bgnerdrunch.android.geoquiz.answer_shown";
    private Button mShowAnswerButton;
    private TextView mAnswerTextView;
    private  TextView mAPI_level;
    private final static  String KEY_COUNT_SHOWN_ANS = "cnt_shown";
    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }
    public  static  boolean wasAnswerShown(Intent result){
        return  result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }
    private boolean mAnswerIsTrue;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAPI_level = findViewById(R.id.API_level_view);
        int n = Build.VERSION.SDK_INT;
        mAPI_level.setText("API level is "+Integer.toString(n));
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        if(3- MainActivity.countAnsShown<= 0) {
            mShowAnswerButton.setEnabled(false);
        }
        else mShowAnswerButton.setEnabled(true);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue)
                    mAnswerTextView.setText(R.string.true_button);
                else
                    mAnswerTextView.setText(R.string.false_button);
                setAnswerShownResult(true);
                int cx = mShowAnswerButton.getWidth();
                int cy = mShowAnswerButton.getHeight();
                float radius = mShowAnswerButton.getShadowRadius();
                Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton,
                        cx,
                        cy,
                        radius,
                        0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationStart(animation);
                        mShowAnswerButton.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                anim.start();
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }
}
