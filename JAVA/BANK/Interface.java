package Bank;

import static Bank.Main.com;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

//介面
class Interface {

    static JFrame frm = new JFrame("TVDI - 銀行系統");
    static JLabel lb = new JLabel();
    static JLabel lb_ = new JLabel();
    static JPanel pl = new JPanel();
    static JComboBox<String> cb1;
    static JComboBox<String> cb2;
    static JComboBox<String> cb3;
    static JButton btn1 = new JButton("設立銀行");
    static JButton btn2 = new JButton("開設帳戶");
    static JButton btn3 = new JButton("建立帳號");
    static JButton btn4 = new JButton("查詢明細");
    static JButton btn5 = new JButton("存　　款");
    static JButton btn6 = new JButton("提　　款");
    static JButton btn7 = new JButton("確　　認");
    static JButton btn8 = new JButton("回主畫面");
    static JTextField tf = new JTextField();

    static void PageMain() {
        Reset("主畫面");

        pl.setLayout(new GridLayout(3, 2, 0, 5));
        pl.add(btn1);
        pl.add(btn2);
        pl.add(btn3);
        pl.add(btn4);
        pl.add(btn5);
        pl.add(btn6);

        frm.remove(btn8);
    }

    static void Page1() {
        Reset("設立銀行");

        pl.setLayout(new GridLayout(2, 1, 0, 5));

        tf.setText("請輸入銀行名稱");
        tf.addFocusListener(new Listener("請輸入銀行名稱"));
        pl.add(tf);

        btn7.addActionListener(e -> {
            Company cc = new Company();
            if (cc.CreateCom()) {
                com.add(cc);
            }
        });
        pl.add(btn7);
    }

    static void Page2() {
        if (Reset("開設帳戶")) {
            return;
        }

        pl.setLayout(new GridLayout(3, 1, 0, 5));

        cb1 = new JComboBox<>();
        com.forEach(c -> cb1.addItem(c.name));
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1);

        tf.setText("請輸入客戶姓名");
        tf.addFocusListener(new Listener("請輸入客戶姓名"));
        pl.add(tf);

        btn7.addActionListener(e -> {
            Client cm = new Client();
            if (cm.CreateMan()) {
                com.get(cb1.getSelectedIndex()).man.add(cm);    //銀行 <- 帳戶
            }
        });
        pl.add(btn7);
    }

    static void Page3() {
        if (Reset("建立帳號")) {
            return;
        }

        pl.setLayout(new GridLayout(4, 1, 0, 5));

        cb1 = new JComboBox<>();
        cb1.addItem("請選擇銀行");
        com.forEach(c -> cb1.addItem(c.name));
        cb1.addActionListener(e -> {
            cb2.removeAllItems();
            if (!com.get(cb1.getSelectedIndex() - 1).man.isEmpty()) {
                com.get(cb1.getSelectedIndex() - 1).man.forEach(c -> cb2.addItem(c.name));
            } else {
                Main.Msg("尚未開設帳戶");
            }
        });
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1);

        cb2 = new JComboBox<>();
        cb2.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb2);

        tf.setText("請輸入開戶金額");
        tf.addFocusListener(new Listener("請輸入開戶金額"));
        pl.add(tf);

        btn7.addActionListener(e -> {
            if (!com.get(cb1.getSelectedIndex() - 1).man.isEmpty()) {
                Account ca = new Account();
                if (ca.CreateAcc()) {
                    com.get(cb1.getSelectedIndex() - 1).man.get(cb2.getSelectedIndex()).acc.add(ca);    //帳戶 <- 帳號
                }
            } else {
                Main.Msg("尚未開設帳戶");
            }
        });
        pl.add(btn7);
    }

    static void Page4() {
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

    static void Page5() {
        if (Reset("存　　款")) {
            return;
        }

        Cash();

        tf.setText("請輸入存款金額");
        tf.addFocusListener(new Listener("請輸入存款金額"));
        pl.add(tf);

        btn7.addActionListener(e -> {
            Account.Cash(5);
        });
        pl.add(btn7);
    }

    static void Page6() {
        if (Reset("提　　款")) {
            return;
        }

        Cash();

        tf.setText("請輸入提款金額");
        tf.addFocusListener(new Listener("請輸入提款金額"));
        pl.add(tf);

        btn7.addActionListener(e -> {
            Account.Cash(6);
        });
        pl.add(btn7);
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

        btn5.addActionListener(e -> {
            Page5();
        });
        btn5.setFont(new Font("標楷體", Font.BOLD, 36));

        btn6.addActionListener(e -> {
            Page6();
        });
        btn6.setFont(new Font("標楷體", Font.BOLD, 36));

        btn7.setFont(new Font("標楷體", Font.BOLD, 36));

        btn8.addActionListener(e -> {
            PageMain();
        });
        btn8.setFont(new Font("標楷體", Font.BOLD, 36));

        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(new Font("標楷體", Font.PLAIN, 36));

        frm.setVisible(true);
    }

    //介面重置
    static boolean Reset(String s) {
        if (!s.equals("主畫面") && !s.equals("設立銀行") && com.isEmpty()) {
            Main.Msg("尚未設立銀行");
            return true;
        } else {
            lb.setText(s);
            pl.removeAll();
            frm.add(btn8, BorderLayout.SOUTH);

            for (ActionListener al : btn7.getActionListeners()) {
                btn7.removeActionListener(al);
            }
            return false;
        }
    }

    //存、提款通用功能
    static void Cash() {
        pl.setLayout(new GridLayout(5, 1, 0, 5));

        cb1 = new JComboBox<>();
        cb1.addItem("請選擇銀行");
        com.forEach(c -> cb1.addItem(c.name));
        cb1.addActionListener(e -> {
            cb2.removeAllItems();
            cb3.removeAllItems();
            if (cb1.getSelectedIndex() > 0 && !com.get(cb1.getSelectedIndex() - 1).man.isEmpty()) {
                cb2.addItem("請選擇帳戶");
                com.get(cb1.getSelectedIndex() - 1).man.forEach(c -> cb2.addItem(c.name));
            } else if (cb1.getSelectedIndex() > 0) {
                Main.Msg("尚未開設帳戶");
            }
        });
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1);

        cb2 = new JComboBox<>();
        cb2.addActionListener(e -> {
            cb3.removeAllItems();
            if (cb2.getSelectedIndex() > 0 && !com.get(cb1.getSelectedIndex() - 1).man.get(cb2.getSelectedIndex() - 1).acc.isEmpty()) {
                com.get(cb1.getSelectedIndex() - 1).man.get(cb2.getSelectedIndex() - 1).acc.forEach(a -> cb3.addItem(a.no));
            } else if (cb2.getSelectedIndex() > 0) {
                Main.Msg("尚未建立帳號");
            }
        });
        cb2.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb2);

        cb3 = new JComboBox<>();
        cb3.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb3);
    }

    //帳戶明細
    static String ListAcc() {
        int i;
        String s;

        if (com.get(cb1.getSelectedIndex()).man.size() > 0) {
            s = "<html><table width='580' border='1'><tr><th>帳戶</th><th>帳號</th><th>餘額</th></tr>";

            for (Client c : com.get(cb1.getSelectedIndex()).man) {
                i = 0;
                s += "<tr><th rowspan='" + c.acc.size() + "'>" + c.name + "</th>";
                for (Account a : c.acc) {
                    s += (i++ > 0 ? "<tr>" : "") + "<td align='center'>" + a.no + "</td><td align='right'>" + a.amount + "</td></tr>";
                }
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