package Bank;
import static Bank.Main.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class Account {
    String no = "";
    int amount = 0;
    
    boolean CreateAcc() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        no = dtf.format(LocalDateTime.now());
        amount = Integer.valueOf(tf1.getText());
        
        if ( amount > 0 ) {
            JOptionPane.showMessageDialog(null, "已完成「"+cb2.getSelectedItem()+"」的「"+no+"」帳號，並存入 "+amount+" 元");
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "建立失敗");      
            return false;
        }      
    }
    
    /*static void Deposit() {
        int db = Integer.valueOf(Main.tf2.getText());
        
        if ( db < 1 )
            JOptionPane.showMessageDialog(null, "金額錯誤");
        else if( d == -1 )
            JOptionPane.showMessageDialog(null, "查無該帳戶");
        else {
            Main.man.get(d).amount += db;
            JOptionPane.showMessageDialog(null, "已完成存款，帳戶餘額 "+Main.man.get(d).amount+" 元");
        }
    }
    
    static void Withdraw() {
        int wb = Integer.valueOf(Main.tf2.getText());
        
        if ( wb < 1 )
            JOptionPane.showMessageDialog(null, "金額錯誤");
        else if( w == -1 )
            JOptionPane.showMessageDialog(null, "查無該帳戶");
        else if ( wb > Main.man.get(w).amount )
            JOptionPane.showMessageDialog(null, "帳戶餘額不足");
        else {
            Main.man.get(w).amount -= wb;
            JOptionPane.showMessageDialog(null, "已完成提款，帳戶餘額 "+Main.man.get(w).amount+" 元");
        }
    }*/
    
}
