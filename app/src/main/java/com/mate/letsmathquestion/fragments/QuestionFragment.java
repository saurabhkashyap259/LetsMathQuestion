package com.mate.letsmathquestion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mate.letsmathquestion.R;
import com.mate.letsmathquestion.helper.DatabaseHandler;
import com.mate.letsmathquestion.models.Answer;
import com.mate.letsmathquestion.models.Question;
import com.mate.letsmathquestion.models.User;
import com.mate.letsmathquestion.utils.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

import io.github.kexanie.library.MathView;

/**
 * Created by sasuke on 26/6/16.
 */
public class QuestionFragment extends Fragment {

    private static final String TAG = QuestionFragment.class.getSimpleName();

    private static MathView description, option1, option2, option3, option4;
    private static RadioGroup optionsLayout;
    private static TextView questionID, answeredBy;
    private static FirebaseUser user;
    private static FirebaseAuth auth;
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public QuestionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        //Local Variables
        List<Question> questionList = new ArrayList<>();
        Button submitButton;
        RadioButton radioButton1, radioButton2, radioButton3, radioButton4;

        //get the ids
        questionID = (TextView) rootView.findViewById(R.id.question_id);
        description = (MathView) rootView.findViewById(R.id.description);
        option1 = (MathView) rootView.findViewById(R.id.option_1);
        option2 = (MathView) rootView.findViewById(R.id.option_2);
        option3 = (MathView) rootView.findViewById(R.id.option_3);
        option4 = (MathView) rootView.findViewById(R.id.option_4);
        radioButton1 = (RadioButton) rootView.findViewById(R.id.radio_button_1);
        radioButton2 = (RadioButton) rootView.findViewById(R.id.radio_button_2);
        radioButton3 = (RadioButton) rootView.findViewById(R.id.radio_button_3);
        radioButton4 = (RadioButton) rootView.findViewById(R.id.radio_button_4);
        optionsLayout = (RadioGroup) rootView.findViewById(R.id.options_layout);
        submitButton = (Button) rootView.findViewById(R.id.button_submit);
        answeredBy = (TextView) rootView.findViewById(R.id.answered_by);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        //Get the questions
        DatabaseHandler.getQuestions();

        DatabaseHandler.getAnswer();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String answer = getAnswer(optionsLayout.getCheckedRadioButtonId());

                DatabaseHandler.addAnswer(user.getUid(), questionID.getText().toString(), answer);
            }
        });

    }

    public static void setValues(List<Question> questionList) {

        questionID.setText(questionList.get(1).getQuestionID());
        description.setText(questionList.get(1).getDes());
        option1.setText(questionList.get(1).getOption1());
        option2.setText(questionList.get(1).getOption2());
        option3.setText(questionList.get(1).getOption3());
        option4.setText(questionList.get(1).getOption4());
    }

    public static String getAnswer(int id) {

        switch (id) {

            case R.id.radio_button_1:
                return option1.getText();

            case R.id.radio_button_2:
                return option2.getText();

            case R.id.radio_button_3:
                return option3.getText();

            case R.id.radio_button_4:
                return option4.getText();

            default:
                return "";
        }
    }

    public static void setAnswer(List<Answer> answerList) {

        String text = "Answered by: ";



        for(Answer answer : answerList) {

            text += answer.getUser_id() + "\n";
        }

        answeredBy.setText(text);
    }

}
