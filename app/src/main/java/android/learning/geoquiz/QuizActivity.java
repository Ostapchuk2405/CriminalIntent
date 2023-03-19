package android.learning.geoquiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private Toast toastResult = null;
    private int[] answers = new int[101];
    private int count = 0;
    private boolean mIsCheater;
    private int numberOfCheat = 0;

    private final Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_0, false),
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, false),
            new Question(R.string.question_4, false),
            new Question(R.string.question_5, true),
            new Question(R.string.question_6, true),
            new Question(R.string.question_7, true),
            new Question(R.string.question_8, true),
            new Question(R.string.question_9, false),
            new Question(R.string.question_10, true),
            new Question(R.string.question_11, false),
            new Question(R.string.question_12, true),
            new Question(R.string.question_13, true),
            new Question(R.string.question_14, true),
            new Question(R.string.question_15, true),
            new Question(R.string.question_16, true),
            new Question(R.string.question_17, true),
            new Question(R.string.question_18, false),
            new Question(R.string.question_19, false),
            new Question(R.string.question_20, false),
            new Question(R.string.question_21, false),
            new Question(R.string.question_22, true),
            new Question(R.string.question_23, false),
            new Question(R.string.question_24, false),
            new Question(R.string.question_25, false),
            new Question(R.string.question_26, true),
            new Question(R.string.question_27, false),
            new Question(R.string.question_28, true),
            new Question(R.string.question_29, true),
            new Question(R.string.question_30, true),
            new Question(R.string.question_31, true),
            new Question(R.string.question_32, true),
            new Question(R.string.question_33, true),
            new Question(R.string.question_34, false),
            new Question(R.string.question_35, false),
            new Question(R.string.question_36, false),
            new Question(R.string.question_37, false),
            new Question(R.string.question_38, false),
            new Question(R.string.question_39, false),
            new Question(R.string.question_40, false),
            new Question(R.string.question_41, false),
            new Question(R.string.question_42, false),
            new Question(R.string.question_43, false),
            new Question(R.string.question_44, false),
            new Question(R.string.question_45, false),
            new Question(R.string.question_46, false),
            new Question(R.string.question_47, false),
            new Question(R.string.question_48, false),
            new Question(R.string.question_49, false),
            new Question(R.string.question_50, false),
            new Question(R.string.question_51, false),
            new Question(R.string.question_52, false),
            new Question(R.string.question_53, true),
            new Question(R.string.question_54, true),
            new Question(R.string.question_55, true),
            new Question(R.string.question_56, true),
            new Question(R.string.question_57, true),
            new Question(R.string.question_58, true),
            new Question(R.string.question_59, true),
            new Question(R.string.question_60, true),
            new Question(R.string.question_61, true),
            new Question(R.string.question_62, true),
            new Question(R.string.question_63, true),
            new Question(R.string.question_64, true),
            new Question(R.string.question_65, true),
            new Question(R.string.question_66, true),
            new Question(R.string.question_67, true),
            new Question(R.string.question_68, true),
            new Question(R.string.question_69, false),
            new Question(R.string.question_70, true),
            new Question(R.string.question_71, true),
            new Question(R.string.question_72, true),
            new Question(R.string.question_73, true),
            new Question(R.string.question_74, true),
            new Question(R.string.question_75, true),
            new Question(R.string.question_76, true),
            new Question(R.string.question_77, true),
            new Question(R.string.question_78, true),
            new Question(R.string.question_79, false),
            new Question(R.string.question_80, false),
            new Question(R.string.question_81, true),
            new Question(R.string.question_82, true),
            new Question(R.string.question_83, true),
            new Question(R.string.question_84, true),
            new Question(R.string.question_85, true),
            new Question(R.string.question_86, true),
            new Question(R.string.question_87, false),
            new Question(R.string.question_88, true),
            new Question(R.string.question_89, false),
            new Question(R.string.question_90, true),
            new Question(R.string.question_91, true),
            new Question(R.string.question_92, true),
            new Question(R.string.question_93, true),
            new Question(R.string.question_94, false),
            new Question(R.string.question_95, true),
            new Question(R.string.question_96, true),
            new Question(R.string.question_97, false),
            new Question(R.string.question_98, true),
            new Question(R.string.question_99, false),
            new Question(R.string.question_100, true)
    };

    private int mCurrentIndex = uniqueQuastion();

    private int uniqueQuastion() {
        int index = 0;
        ArraySet<Integer> questionIndexes = new ArraySet<Integer>(101);
        for (int i = 0; i <= 100; i++) {
            index = (int) (Math.random() * 101);
            if(!questionIndexes.contains(index)){
                questionIndexes.add(index);
                break;
            }
        }
        return index;
    }

    private int messageResId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_type);
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = uniqueQuastion();
                mIsCheater = false;
                updateQuestion();
                buttonsActive(true);
            }
        });

        mCheatButton =(Button) findViewById(R.id.cheat_button);
        mCheatButton.setEnabled(numberOfCheat < 3 ? true : false);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfCheat += 1;
                int probability = (int) (Math.random() * 101);
                boolean answer_is_true = probability < 76 ? mQuestionBank[mCurrentIndex].isAnswerTrue() : !mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answer_is_true);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    private void updateQuestion() {
        Log.d(TAG, "Updating question text", new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        buttonsActive(false);
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void buttonsActive(boolean isAnswerChoosed) {
        mTrueButton.setEnabled(isAnswerChoosed);
        mFalseButton.setEnabled(isAnswerChoosed);
        mNextButton.setEnabled(!isAnswerChoosed);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public int mCurrentIndex() {
        return mCurrentIndex;
    }
}