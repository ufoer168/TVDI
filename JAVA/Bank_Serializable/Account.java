package Bank_Serializable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//帳號建構子
public class Account implements Serializable {

    String no = "";     //帳號
    int amount = 0;     //款項

    //建立帳號
    boolean CreateAcc() {
        try {
            no = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());     //以時間產生帳號
            amount = Integer.valueOf(Interface.tf.getText());

            if (amount > 0) {
                Main.Msg("已建立「" + Interface.cb1.getSelectedItem() + "」的「" + no + "」帳號，並存入 " + amount + " 元");
                return true;
            } else {
                Main.Msg("建立失敗");
                return false;
            }
        } catch (NumberFormatException e) {
            Main.Msg("請輸入整數值");
            return false;
        }
    }

    //存、提款
    static void Cash() {
        try {
            int aa = Main.com.get(Interface.cb1.getSelectedIndex()).acc.get(Interface.cb2.getSelectedIndex()).amount;
            int ctf = Integer.valueOf(Interface.tf.getText());

            if (ctf < 1) {
                Main.Msg("金額錯誤");
            } else if (Math.abs(ctf) > aa) {
                Main.Msg("餘額不足");
            } else {
                int sum = aa + ctf;

                Main.com.get(Interface.cb1.getSelectedIndex()).acc.get(Interface.cb2.getSelectedIndex()).amount = sum;
                Main.Msg("已完成" + (ctf > 0 ? "存" : "提") + "款，帳戶餘額 " + sum + " 元");
            }
        } catch (NumberFormatException e) {
            Main.Msg("請輸入整數值");
        }
    }
}
