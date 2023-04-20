package Bank_Serializable;

import static Bank_Serializable.Main.com;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//介面
class Interface {

    static JFrame frm = new JFrame("TVDI - 銀行系統");
    static JLabel lb = new JLabel();
    static JLabel lb_ = new JLabel();
    static JPanel pl = new JPanel();
    static JComboBox<String> cb1;
    static JComboBox<String> cb2;
    static JButton btn1 = new JButton("開設帳戶");
    static JButton btn2 = new JButton("建立帳號");
    static JButton btn3 = new JButton("查詢明細");
    static JButton btn4 = new JButton("存(提)款");
    static JButton btn5 = new JButton("確　　認");
    static JButton btn6 = new JButton("回主畫面");
    static JTextField tf = new JTextField();

    static void PageMain() {
        Reset("主畫面");

        pl.setLayout(new GridLayout(2, 2, 0, 5));
        pl.add(btn1);
        pl.add(btn2);
        pl.add(btn3);
        pl.add(btn4);

        frm.remove(btn6);
    }

    static void Page1() {
        Reset("開設帳戶");

        pl.setLayout(new GridLayout(2, 1, 0, 5));

        tf.setText("請輸入客戶姓名");
        tf.addFocusListener(new Listener("請輸入客戶姓名"));
        pl.add(tf);

        btn5.addActionListener(e -> {
            Client c = new Client();
            if (c.CreateMan()) {
                com.add(c);
            }
        });
        pl.add(btn5);
    }

    static void Page2() {
        if (Reset("建立帳號")) {
            return;
        }

        pl.setLayout(new GridLayout(3, 1, 0, 5));

        cb1 = new JComboBox<>();
        if (!com.isEmpty()) {
                com.forEach(c -> cb1.addItem(c.name));
            } else {
                Main.Msg("尚未開設帳戶");
            }
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1);

        tf.setText("請輸入開戶金額");
        tf.addFocusListener(new Listener("請輸入開戶金額"));
        pl.add(tf);

        btn5.addActionListener(e -> {
            if (!com.isEmpty()) {
                Account a = new Account();
                if (a.CreateAcc()) {
                    com.get(cb1.getSelectedIndex()).acc.add(a);    //帳戶 <- 帳號
                }
            } else {
                Main.Msg("尚未開設帳戶");
            }
        });
        pl.add(btn5);
    }

    static void Page3() {
        if (Reset("查詢明細")) {
            return;
        }

        pl.setLayout(new BorderLayout());

        cb1 = new JComboBox<>();
        com.forEach(c -> cb1.addItem(c.name));
        cb1.addActionListener(e -> {
            lb_.setText(ListAcc());
        });
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1, BorderLayout.NORTH);

        lb_.setText(ListAcc());
        pl.add(lb_, BorderLayout.CENTER);
    }

    static void Page4() {
        if (Reset("存(提)款")) {
            return;
        }
        
        pl.setLayout(new GridLayout(4, 1, 0, 5));

        cb1 = new JComboBox<>();
        com.forEach(c -> cb1.addItem(c.name));
        cb1.addActionListener(e -> {
            cb2.removeAllItems();
            if (!com.get(cb1.getSelectedIndex()).acc.isEmpty()) {
                com.get(cb1.getSelectedIndex()).acc.forEach(a -> cb2.addItem(a.no));
            } else if (cb1.getSelectedIndex() > 0) {
                Main.Msg("尚未建立帳號");
            }
        });        
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1);

        cb2 = new JComboBox<>();
        com.get(cb1.getSelectedIndex()).acc.forEach(a -> cb2.addItem(a.no));
        cb2.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb2);

        tf.setText("正為存款、負為提款");
        tf.addFocusListener(new Listener("正為存款、負為提款"));
        pl.add(tf);

        btn5.addActionListener(e -> {
            Account.Cash();
        });
        pl.add(btn5);
    }

    //基本設定
    static void Style() {
        frm.setSize(600, 600);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLayout(new BorderLayout());
        frm.add(lb, BorderLayout.NORTH);
        frm.add(pl, BorderLayout.CENTER);

        lb.setHorizontalAlignment(JLabel.CENTER);
        lb.setFont(new Font("標楷體", Font.BOLD, 36));

        lb_.setHorizontalAlignment(JLabel.CENTER);
        lb_.setFont(new Font("標楷體", Font.PLAIN, 24));

        btn1.addActionListener(e -> {
            Page1();
        });
        btn1.setFont(new Font("標楷體", Font.BOLD, 36));

        btn2.addActionListener(e -> {
            Page2();
        });
        btn2.setFont(new Font("標楷體", Font.BOLD, 36));

        btn3.addActionListener(e -> {
            Page3();
        });
        btn3.setFont(new Font("標楷體", Font.BOLD, 36));

        btn4.addActionListener(e -> {
            Page4();
        });
        btn4.setFont(new Font("標楷體", Font.BOLD, 36));

        btn5.setFont(new Font("標楷體", Font.BOLD, 36));

        btn6.addActionListener(e -> {
            PageMain();
        });
        btn6.setFont(new Font("標楷體", Font.BOLD, 36));

        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(new Font("標楷體", Font.PLAIN, 36));

        frm.setVisible(true);
        
        frm.addWindowListener(new WindowAdapter() {  
            @Override
            public void windowClosing(WindowEvent w) {
                try {
                    Record rd = new Record();
                    rd.Save(com);
                }
                catch(Exception e) {
                    //e.printStackTrace();
                }
            }

            @Override
            public void windowOpened(WindowEvent w) {
                try {
                    Record rd = new Record();
                    rd.Read();
                }
                catch(Exception e) {
                    //e.printStackTrace();
                }
            }            
        });
    }

    //介面重置
    static boolean Reset(String s) {
        if (!s.equals("主畫面") && !s.equals("開設帳戶") && com.isEmpty()) {
            Main.Msg("尚未開設帳戶");
            return true;
        } else {
            lb.setText(s);
            pl.removeAll();
            frm.add(btn6, BorderLayout.SOUTH);

            for (ActionListener al : btn5.getActionListeners()) {
                btn5.removeActionListener(al);
            }
            return false;
        }
    }

    //帳戶明細
    static String ListAcc() {
        int i;
        String s;

        if (com.get(cb1.getSelectedIndex()).acc.size() > 0) {
            s = "<html><table width='580' border='1'><tr><th>帳號</th><th>餘額</th></tr>";

            for (Account a : com.get(cb1.getSelectedIndex()).acc) {
                s += "<tr><td align='center'>" + a.no + "</td><td align='right'>" + a.amount + "</td></tr>";
            }

            s += "</table></html>";
            lb_.setVerticalAlignment(JLabel.TOP);
        } else {
            s = "查無帳戶";
            lb_.setVerticalAlignment(JLabel.CENTER);
        }
        return s;
    }

}