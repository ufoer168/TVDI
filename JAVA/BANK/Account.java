package Bank;

import static Bank.Main.com;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

//帳號建構子
public class Account {

    String no = "";
    int amount = 0;

    //建立帳號
    boolean CreateAcc() {
        no = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        amount = Integer.valueOf(Interface.tf.getText());

        if (amount > 0) {
            JOptionPane.showMessageDialog(null, "已建立「" + Interface.cb2.getSelectedItem() + "」的「" + no + "」帳號，並存入 " + amount + " 元");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "建立失敗");
            return false;
        }
    }

    //存、提款
    static void Cash(int i) {
        int aa = Main.com.get(Interface.cb1.getSelectedIndex() - 1).man.get(Interface.cb2.getSelectedIndex() - 1).acc.get(Interface.cb3.getSelectedIndex()).amount;
        int ctf = Integer.valueOf(Interface.tf.getText());

        if (ctf < 1) {
            JOptionPane.showMessageDialog(null, "金額錯誤");
        } else if (i == 6 && ctf > aa) {
            JOptionPane.showMessageDialog(null, "餘額不足");
        } else {
            int sum = aa + (i == 5 ? ctf : -ctf);

            Main.com.get(Interface.cb1.getSelectedIndex() - 1).man.get(Interface.cb2.getSelectedIndex() - 1).acc.get(Interface.cb3.getSelectedIndex()).amount = sum;
            JOptionPane.showMessageDialog(null, "已完成" + (i == 5 ? "存" : "提") + "款，帳戶餘額 " + sum + " 元");
        }
    }
}
