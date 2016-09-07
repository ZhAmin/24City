package com.example.utils;

import android.text.Selection;
import android.text.Spannable;
import android.widget.TextView;

/**
 * Created by hmz on 2015/11/6.
 */
public class TextSelectUtils {
    public static void setCurrentCursorIndex(TextView textView){
        CharSequence text = textView.getText();
        //Debug.asserts(text instanceof Spannable);
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }
}
