package edu.gatech.seclass.projectcode.activities;

import android.app.AlertDialog;
import android.view.View;

public class TwoButtonDialogue {

    //If no custom buttons we will use defaults
    public static AlertDialog TwoButtonDialogue(View view, String title, String message){
        return TwoButtonDialogue(view, title, message, "Confirm", "Cancel");
    }

    public static AlertDialog TwoButtonDialogue(View view, String title, String message, String confirm, String cancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(confirm,null);
        builder.setNegativeButton(cancel,null);
        AlertDialog dialog = builder.create();
        dialog.show();
        return  dialog;
    }
}
