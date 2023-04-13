package Bank;

import java.util.ArrayList;

//銀行建構子
class Company {

    String name = "";   //銀行名稱
    ArrayList<Client> man = new ArrayList<>();  //宣告帳戶

    //設立銀行
    boolean CreateCom() {
        name = Interface.tf.getText();

        if (!name.isEmpty() && !name.equals("請輸入銀行名稱")) {
            for (Company c : Main.com) {
                if (c.name.equals(name)) {
                    Main.Msg("銀行名稱重複");
                    return false;
                }
            }

            Main.Msg("已完成「" + name + "」銀行設立");
            return true;
        } else {
            Main.Msg("銀行名稱錯誤");
            return false;
        }
    }

}