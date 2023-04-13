package Bank;

import java.util.ArrayList;
import javax.swing.JOptionPane;

//銀行建構子
class Company {

    String name = "";
    ArrayList<Client> man = new ArrayList<>();

    //設立銀行
    boolean CreateCom() {
        name = Interface.tf.getText();

        if (!name.isEmpty() && !name.equals("請輸入銀行名稱")) {
            for (Company c : Main.com) {
                if (c.name.equals(name)) {
                    JOptionPane.showMessageDialog(null, "銀行名稱重複");
                    return false;
                }
            }

            JOptionPane.showMessageDialog(null, "已完成「" + name + "」銀行設立");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "銀行名稱錯誤");
            return false;
        }
    }

}
