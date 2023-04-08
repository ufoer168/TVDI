package Bank;

import java.util.ArrayList;
import javax.swing.JOptionPane;

//帳戶建構子
class Client {

    String name = "";
    ArrayList<Account> acc = new ArrayList<>();

    //開設帳戶
    boolean CreateMan() {
        name = Interface.tf.getText();

        if (!name.isEmpty() && !name.equals("請輸入客戶姓名")) {
            for (Client c : Main.com.get(Interface.cb1.getSelectedIndex()).man) {
                if (c.name.equals(name)) {
                    JOptionPane.showMessageDialog(null, "客戶姓名重複");
                    return false;
                }
            }

            JOptionPane.showMessageDialog(null, "已完成「" + name + "」開戶");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "客戶姓名錯誤");
            return false;
        }
    }

}
