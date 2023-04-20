package Bank_HashMap;

import static Bank_HashMap.Interface.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class MapData implements Serializable {
    
    private HashMap<String, HashMap<String, Integer>> com = new HashMap();
    private HashMap<String, Integer> acc;
    
    void CreateMan() {
        String name = tf.getText();

        if (!name.isEmpty() && !name.equals("請輸入客戶姓名")) {
            if (com.containsKey(name)) {
                Main.Msg("客戶姓名重複");
            } else {
                com.put(name, null);
                Main.Msg("已完成「" + name + "」開戶");
            }

        } else {
            Main.Msg("客戶姓名錯誤");
        }
    }
    
    void CreateAcc() {
        String name = (String)cb1.getSelectedItem();
        
        try {
            String no = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());     //以時間產生帳號
            int amount = Integer.valueOf(tf.getText());

            if (amount > 0) {
                acc = CheckAcc(name);
                acc.put(no, amount);
                com.put(name, acc);
                Main.Msg("已建立「" + name + "」的「" + no + "」帳號，並存入 " + amount + " 元");
            } else {
                Main.Msg("建立失敗");
            }
        } catch (NumberFormatException e) {
            Main.Msg("請輸入整數值");
        }
    }
    
    void Cash() {
        String name = (String)cb1.getSelectedItem();
        String no = (String)cb2.getSelectedItem();
        
        try {
            int aa = com.get(name).get(no);
            int ctf = Integer.valueOf(tf.getText());

            if (ctf < 0 && Math.abs(ctf) > aa) {
                Main.Msg("餘額不足");
            } else {
                int sum = aa + ctf;

                acc = CheckAcc(name);
                acc.put(no, sum);
                com.put(name, acc);
                Main.Msg("已完成" + (ctf > 0 ? "存" : "提") + "款，帳戶餘額 " + sum + " 元");
            }
        } catch (NumberFormatException e) {
            Main.Msg("請輸入整數值");
        }
    }
    
    HashMap<String, Integer> CheckAcc(String name) {
        if (com.containsKey(name) && com.get(name) != null) {
            return com.get(name);
        } else {
            return new HashMap();
        }
    }
    
    void ReadDate(HashMap<String, HashMap<String, Integer>> m) {
        if (m != null) {
            com = m;
        }
    }
    
    HashMap<String, HashMap<String, Integer>> GetData() {
        return com;
    }
    
}
