package Mystical.Mist.Functionalities;

import android.widget.EditText;

public class Field {

    public static boolean checkInputs(EditText... textFields) {
        boolean finalOutcome = false;

        for(EditText textField : textFields) {
            if(textField.getText().toString().trim().isEmpty()) { return true; }
        }

        return finalOutcome;
    }

    public static void clearTexts(EditText... textFields) {
        for(EditText textField : textFields) {
            textField.getText().clear();
        }
    }

    public static String getText(EditText textField) {
        return textField.getText().toString();
    }

}
