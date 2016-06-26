package com.mate.letsmathquestion.helper;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mate.letsmathquestion.fragments.QuestionFragment;
import com.mate.letsmathquestion.models.Answer;
import com.mate.letsmathquestion.models.Question;
import com.mate.letsmathquestion.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sasuke on 26/6/16.
 */
public class DatabaseHandler {

    private static final String TAG = DatabaseHandler.class.getSimpleName();

    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public static boolean addUser(String provider, AuthResult result) {

        FirebaseUser user = result.getUser();
        Map<String, Object> map = new HashMap<>();

        String email = user.getEmail();
        String name = user.getDisplayName();
        Uri profileURL = user.getPhotoUrl();

        if(name != null) {

            map.put("name", name);
        }

        if(profileURL != null) {

            map.put("profileURL", profileURL.toString());
        }

        map.put("id", user.getUid());

        Log.e(TAG, "Map: " + map);

        if(email != null) {

            map.put("email", email);
            String username = email.split("@")[0];
            firebaseDatabase.getReference("users").child(provider).child(username).setValue(map);
            return true;
        } else {

            Log.e(TAG, "Email is null");
            return false;
        }
    }

    public static boolean addQuestion(Question question) {

        Map<String, Object> map = new HashMap<>();

        map.put("des", question.getDes());
        map.put("option1", question.getOption1());
        map.put("option2", question.getOption2());
        map.put("option3", question.getOption3());
        map.put("option4", question.getOption4());
        map.put("answer", question.getAnswer());

        Task task = firebaseDatabase.getReference("questions").push().setValue(map);

        if(task.isSuccessful()) {

            return true;
        }else {

            return false;
        }
    }

    public static void getQuestions() {

        final List<Question> questionList = new ArrayList<>();

        DatabaseReference reference = firebaseDatabase.getReference("questions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()) {

                    for(DataSnapshot data : dataSnapshot.getChildren()) {

                        //Question question = new Question();

                        Question question = data.getValue(Question.class);

                        question.setQuestionID(data.getKey());

                        questionList.add(question);

                    }

                    QuestionFragment.setValues(questionList);
                } else {

                    Log.e(TAG, "Data Snap Shot has no child");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e(TAG, "The read failed: " + databaseError.getMessage());
            }
        });

    }

    public static void addAnswer(String userID, String questionID, String answer) {

        Map<String, Object> map = new HashMap<>();

        map.put("answer", answer);
        map.put("question_id", questionID);
        map.put("user_id", userID);

        firebaseDatabase.getReference("answers").push().setValue(map);
    }

    public static void getAnswer() {

        DatabaseReference reference = firebaseDatabase.getReference("answers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Answer> answerList = new ArrayList<>();

                if(dataSnapshot.hasChildren()) {

                    for(DataSnapshot data : dataSnapshot.getChildren()) {

                        //Question question = new Question();

                        Answer answer = data.getValue(Answer.class);

                        Log.e(TAG, "Answer: " + answer.getAnswer() + " " + answer.getQuestion_id() + " " + answer.getUser_id());

                        answer.setAnswerID(data.getKey());

                        answerList.add(answer);

                    }

                    Log.e(TAG, "Answer list size: " + answerList.size());

                    QuestionFragment.setAnswer(answerList);
                } else {

                    Log.e(TAG, "Data Snap Shot has no child");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e(TAG, "The read failed: " + databaseError.getMessage());
            }
        });
    }
}
