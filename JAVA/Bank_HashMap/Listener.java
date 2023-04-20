package Bank_HashMap;

import static Bank_HashMap.Interface.tf;
import java.awt.event.*;

//監聽文字框
class Listener implements FocusListener {

    static String s = "";

    Listener(String w) {
        s = w;
    }

    //清除、顯示預設文字
    public void focusGained(FocusEvent e) {
        if (tf.getText().equals(s)) {
            tf.setText("");
        }
    }

    public void focusLost(FocusEvent e) {
        if (tf.getText().equals("")) {
            tf.setText(s);
        }
    }

}