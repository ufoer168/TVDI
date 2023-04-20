package Bank_MAP;

import javax.swing.JOptionPane;

//主程式
class Main {

    public static void main(String[] args) {        
        Interface.Style();      //載入介面
        Interface.PageMain();  //開啟主畫面
    }
    
    //錯誤提示
    static void Msg(String s) {
        JOptionPane.showMessageDialog(null, s);
    }

}