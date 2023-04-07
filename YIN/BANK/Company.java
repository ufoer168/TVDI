package Bank;
import static Bank.Main.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

class Company {
    String name = "";
    ArrayList<Client> man = new ArrayList<>();
    
    boolean CreateCom() {
        name = Main.tf1.getText();
        boolean check = false;
        
        if ( !name.isEmpty() && !name.equals("請輸入銀行名稱") ) {
            for ( int i = 0; i < Main.com.size(); i++ ) {
                if( Main.com.get(i).name.equals(name) )
                    check = true;
            }
            
            if ( check ) {
                JOptionPane.showMessageDialog(null, "銀行名稱重複");
                return false;
            }
            
            JOptionPane.showMessageDialog(null, "已完成「"+name+"」銀行設立");
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "銀行名稱錯誤");      
            return false;
        }      
    }
    
}
