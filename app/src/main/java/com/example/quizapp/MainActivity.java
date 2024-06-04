
package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button[] choiceButtons;
    private Button nextButton;
    private TextView scoreTextView;

    private List<Question> questionList;
    private int currentQuestionIndex;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        choiceButtons = new Button[]{
                findViewById(R.id.choice1Button),
                findViewById(R.id.choice2Button),
                findViewById(R.id.choice3Button),
                findViewById(R.id.choice4Button)
        };
        nextButton = findViewById(R.id.nextButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        questionList = generateSampleQuestions();
        currentQuestionIndex = 0;
        score = 0;

        loadQuestion();

        for (int i = 0; i < choiceButtons.length; i++) {
            int finalI = i;
            choiceButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(finalI);
                }
            });
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    loadQuestion();
                } else {
                    showFinalScore();
                }
            }
        });
    }

    private void loadQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestionText());
        String[] choices = currentQuestion.getChoices();
        for (int i = 0; i < choiceButtons.length; i++) {
            choiceButtons[i].setText(choices[i]);
        }
    }

    private void checkAnswer(int chosenIndex) {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        if (chosenIndex == currentQuestion.getCorrectAnswerIndex()) {
            score++;
        }
        scoreTextView.setText("Score: " + score);

    }

    private void showFinalScore() {
        questionTextView.setText("Quiz Finished! Your score is " + score);
        for (Button button : choiceButtons) {
            button.setVisibility(View.GONE);
        }
        nextButton.setVisibility(View.GONE);
    }

    private List<Question> generateSampleQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Which of these is a valid keyword in Java?", new String[]{"interface", "string", "Float", "unsigned"}, 0));
        questions.add(new Question("What is the size of int in Java?", new String[]{"16 bits", "32 bits", "64 bits", "Depends on the underlying platform"}, 1));
        questions.add(new Question("Which of the following is not a Java feature?", new String[]{"Dynamic", "Architecture Neutral", "Use of Pointers", "Object-oriented"}, 2));
        questions.add(new Question("What is the return type of the hashCode() method in the Object class?", new String[]{"int", "Object", "long", "void"}, 0));
        questions.add(new Question("Which of these cannot be used for a variable name in Java?", new String[]{"identifier", "keyword", "variable", "none of the above"}, 1));
        return questions;
    }
}
