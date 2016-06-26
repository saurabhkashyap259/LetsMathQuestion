package com.mate.letsmathquestion.utils;

import com.mate.letsmathquestion.helper.DatabaseHandler;
import com.mate.letsmathquestion.models.Question;

/**
 * Created by sasuke on 26/6/16.
 */
public class AppData {

    public static void addAppData() {

        String question1 = "Find the zeroes of the quadratic polynomial " + "\\(x^2 +7x + 12\\)";
        String question2 = "Find the zeroes of the polynomial " +"\\(x^2 - 17\\)";

        DatabaseHandler.addQuestion(new Question(question1, "-2, -5", "-3, -4", "2, 5", "3, 4", "-3, -4"));
        DatabaseHandler.addQuestion(new Question(question2, "sqrt(17), sqrt(-17)", "sqrt(3), sqrt(-3)", "sqrt(19), sqrt(-19",
                "None of these", "sqrt(17), sqrt(-17)"));
    }
}
