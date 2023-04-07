package Bank;
import static Bank.Main.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

class Client {
    String name = "";
    ArrayList<Account> acc = new ArrayList<>();
    
    boolean CreateMan() {
        name = Main.tf1.getText();
        boolean check = false;
        
        if ( !name.isEmpty() && !name.equals("請輸入客戶姓名") ) {
            for ( int i = 0; i < com.get(cb1.getSelectedIndex()).man.size(); i++ ) {
                if( com.get(cb1.getSelectedIndex()).man.get(i).name.equals(name) )
                    check = true;
            }
            
            if ( check ) {
                JOptionPane.showMessageDialog(null, "客戶姓名重複");
                return false;
            }
            
            JOptionPane.showMessageDialog(null, "已完成「"+name+"」開戶");
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "客戶姓名錯誤");      
            return false;
        }      
    }
    
}