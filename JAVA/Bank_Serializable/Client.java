package Bank_Serializable;

import java.io.Serializable;
import java.util.ArrayList;

//帳戶建構子
class Client implements Serializable {

    String name = "";   //帳戶名稱
    ArrayList<Account> acc = new ArrayList<>(); //宣告帳號

    //開設帳戶
    boolean CreateMan() {
        name = Interface.tf.getText();

        if (!name.isEmpty() && !name.equals("請輸入客戶姓名")) {
            for (Client c : Main.com) {
                if (c.name.equals(name)) {
                    Main.Msg("客戶姓名重複");
                    return false;
                }
            }

            Main.Msg("已完成「" + name + "」開戶");
            return true;
        } else {
            Main.Msg("客戶姓名錯誤");
            return false;
        }
    }

}