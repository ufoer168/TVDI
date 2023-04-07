package Bank;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

class Main {

    static ArrayList<Company> com = new ArrayList<>();
    static String click = "";

    static JFrame frm = new JFrame("銀行系統");
    static JLabel lb = new JLabel();
    static JLabel lb_ = new JLabel();
    static JPanel pl = new JPanel();
    static JComboBox<String> cb1;
    static JComboBox<String> cb2;
    static JComboBox<String> cb3;
    static JButton btn1 = new JButton("設立銀行");
    static JButton btn1_ = new JButton("確認設立");
    static JButton btn2 = new JButton("開設帳戶");
    static JButton btn2_ = new JButton("確認開設");
    static JButton btn3 = new JButton("建立帳號");
    static JButton btn3_ = new JButton("確認建立");
    static JButton btn4 = new JButton("查詢餘額");
    static JButton btn4_ = new JButton("查詢");
    static JButton btn5 = new JButton("存　　款");
    static JButton btn5_ = new JButton("確認存款");
    static JButton btn6 = new JButton("提　　款");
    static JButton btn6_ = new JButton("確認提款");
    static JButton btn7 = new JButton("回主畫面");
    static JTextField tf1 = new JTextField();
    static JTextField tf2 = new JTextField();

    public static void main(String[] args) {
        Style();
        PageMain();
        frm.setVisible(true);
    }

    static void PageMain() {
        Reset("主畫面");

        pl.setLayout(new GridLayout(3, 2, 0, 5));
        pl.add(btn1);
        pl.add(btn2);
        pl.add(btn3);
        pl.add(btn4);
        pl.add(btn5);
        pl.add(btn6);

        frm.remove(btn7);
    }

    static void Page1() {
        Reset("設立銀行");

        pl.setLayout(new GridLayout(2, 1, 0, 5));
        tf1.setText("請輸入銀行名稱");
        tf1.addFocusListener(new FocusListener() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if ( tf1.getText().equals("請輸入銀行名稱") )
                    tf1.setText("");
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if ( tf1.getText().equals("") )
                    tf1.setText("請輸入銀行名稱");
            }        
        });
        pl.add(tf1);
        
        if (!click.contains("1")) {
            btn1_.addActionListener((e) -> {
                Company cc = new Company();
                if (cc.CreateCom())
                    com.add(cc);
            });

            click += "1";
        }
        pl.add(btn1_);

        frm.add(btn7, BorderLayout.SOUTH);
    }

    static void Page2() {
        Reset("開設帳戶");

        if (com.size() == 0) {
            JOptionPane.showMessageDialog(null, "尚未設立銀行");
            PageMain();
            return;
        }

        pl.setLayout(new GridLayout(3, 1, 0, 5));

        cb1 = new JComboBox<>();
        for (Company c : com)
            cb1.addItem(c.name);
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1);

        tf1.setText("請輸入客戶姓名");
        tf1.addFocusListener(new FocusListener() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if ( tf1.getText().equals("請輸入客戶姓名") )
                    tf1.setText("");
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if ( tf1.getText().equals("") )
                    tf1.setText("請輸入客戶姓名");
            }        
        });
        pl.add(tf1);

        if (!click.contains("2")) {
            btn2_.addActionListener((e) -> {
                Client cm = new Client();
                if (cm.CreateMan())
                    com.get(cb1.getSelectedIndex()).man.add(cm);
            });

            click += "2";
        }
        pl.add(btn2_);

        frm.add(btn7, BorderLayout.SOUTH);
    }

    static void Page3() {
        Reset("建立帳號");

        if (com.size() == 0) {
            JOptionPane.showMessageDialog(null, "尚未設立銀行");
            PageMain();
            return;
        }

        pl.setLayout(new GridLayout(4, 1, 0, 5));

        cb1 = new JComboBox<>();
        for (Company c : com)
            cb1.addItem(c.name);
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1);

        if (com.get(cb1.getSelectedIndex()).man.size() == 0) {
            JOptionPane.showMessageDialog(null, "尚未開設帳戶");
            PageMain();
            return;
        }

        cb2 = new JComboBox<>();
        for (Client c : com.get(cb1.getSelectedIndex()).man)
            cb2.addItem(c.name);
        cb2.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb2);

        tf1.setText("請輸入開戶金額");
        tf1.addFocusListener(new FocusListener() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if ( tf1.getText().equals("請輸入開戶金額") )
                    tf1.setText("");
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if ( tf1.getText().equals("") )
                    tf1.setText("請輸入開戶金額");
            }        
        });
        pl.add(tf1);

        cb1.addActionListener((e) -> {
            cb2.removeAllItems();
            if (com.get(cb1.getSelectedIndex()).man.size() > 0) {
                for (Client c : com.get(cb1.getSelectedIndex()).man)
                    cb2.addItem(c.name);
            }
            else
                JOptionPane.showMessageDialog(null, "尚未開設帳戶");
        });

        if (!click.contains("3")) {
            btn3_.addActionListener((e) -> {
                if (com.get(cb1.getSelectedIndex()).man.size() > 0) {
                    Account ca = new Account();
                    if (ca.CreateAcc())
                        com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.add(ca);
                }
                else
                    JOptionPane.showMessageDialog(null, "尚未開設帳戶");
            });

            click += "3";
        }
        pl.add(btn3_);

        frm.add(btn7, BorderLayout.SOUTH);
    }

    static void Page4() {
        Reset("查詢餘額");

        if (com.size() == 0) {
            JOptionPane.showMessageDialog(null, "尚未設立銀行");
            PageMain();
            return;
        }
        
        pl.setLayout(new BorderLayout());
        
        cb1 = new JComboBox<>();
        for (Company c : com)
            cb1.addItem(c.name);
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1, BorderLayout.NORTH);
        
        lb_.setText("<html>"+AccList()+"</html>");
        pl.add(lb_);
        
        cb1.addActionListener((e) -> {
            lb_.setText("<html>"+AccList()+"</html>");
        });

        /*pl.setLayout(new GridLayout(4, 1, 0, 5));

        cb1 = new JComboBox<>();
        for (int i = 0; i < com.size(); i++)
            cb1.addItem(com.get(i).name);
        cb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb1);

        if (com.get(cb1.getSelectedIndex()).man.size() == 0) {
            JOptionPane.showMessageDialog(null, "尚未開設帳戶");
            PageMain();
            return;
        }

        cb2 = new JComboBox<>();
        for (int i = 0; i < com.get(cb1.getSelectedIndex()).man.size(); i++)
            cb2.addItem(com.get(cb1.getSelectedIndex()).man.get(i).name);
        cb2.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb2);

        if (com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.size() == 0) {
            JOptionPane.showMessageDialog(null, "尚未建立帳號");
            PageMain();
            return;
        }

        cb3 = new JComboBox<>();
        for (int i = 0; i < com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.size(); i++)
            cb3.addItem(com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.get(i).no);
        cb3.setFont(new Font("標楷體", Font.BOLD, 36));
        pl.add(cb3);

        cb1.addActionListener((e) -> {
            cb2.removeAllItems();
            if (com.get(cb1.getSelectedIndex()).man.size() > 0) {
                for (int i = 0; i < com.get(cb1.getSelectedIndex()).man.size(); i++)
                    cb2.addItem(com.get(cb1.getSelectedIndex()).man.get(i).name);
                
                cb3.removeAllItems();
                if (com.get(cb1.getSelectedIndex()).man.get(0).acc.size() > 0) {
                    for (int i = 0; i < com.get(cb1.getSelectedIndex()).man.get(0).acc.size(); i++)
                        cb3.addItem(com.get(cb1.getSelectedIndex()).man.get(0).acc.get(i).no);
                }
                else
                    JOptionPane.showMessageDialog(null, "尚未建立帳號");
            }
            else
                JOptionPane.showMessageDialog(null, "尚未開設帳戶");
            });

            cb2.addActionListener((e) -> {
                cb3.removeAllItems();
                if (com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.size() > 0) {
                    for (int i = 0; i < com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.size(); i++)
                        cb3.addItem(com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.get(i).no);
                }
                else
                    JOptionPane.showMessageDialog(null, "尚未建立帳號");
            });

        if (!click.contains("4")) {
            btn4_.addActionListener((e) -> {
                if (!com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.get(cb3.getSelectedIndex()).no.isEmpty())
                    JOptionPane.showMessageDialog(null, "帳戶餘額 " + com.get(cb1.getSelectedIndex()).man.get(cb2.getSelectedIndex()).acc.get(cb3.getSelectedIndex()).amount + " 元");
                else
                    JOptionPane.showMessageDialog(null, "查無該帳戶");
            });

            click += "4";
        }
        pl.add(btn4_);*/

        frm.add(btn7, BorderLayout.SOUTH);
    }

    static void Page5() {
        Reset("存　　款");

        pl.setLayout(new GridLayout(3, 1, 0, 5));
        tf1.setText("請輸入帳戶名稱");
        pl.add(tf1);
        tf2.setText("請輸入存款金額");
        pl.add(tf2);
        btn5_.addActionListener((e) -> {
            //Account.Deposit();
        });
        pl.add(btn5_);
        frm.add(pl);

        frm.add(btn7, BorderLayout.SOUTH);
    }

    static void Page6() {
        Reset("提　　款");

        pl.setLayout(new GridLayout(3, 1, 0, 5));
        tf1.setText("請輸入帳戶名稱");
        pl.add(tf1);
        tf2.setText("請輸入提款金額");
        pl.add(tf2);
        btn6_.addActionListener((e) -> {
            //Account.Withdraw();
        });
        pl.add(btn6_);
        frm.add(pl);

        frm.add(btn7, BorderLayout.SOUTH);
    }

    static void Style() {
        frm.setSize(600, 600);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLayout(new BorderLayout());

        lb.setHorizontalAlignment(JLabel.CENTER);
        lb.setFont(new Font("標楷體", Font.BOLD, 36));
        lb_.setFont(new Font("標楷體", Font.PLAIN, 24));
        frm.add(lb, BorderLayout.NORTH);
        frm.add(pl, BorderLayout.CENTER);

        btn1.addActionListener((e) -> {
            Page1();
        });
        btn1.setFont(new Font("標楷體", Font.BOLD, 36));
        btn2.addActionListener((e) -> {
            Page2();
        });
        btn2.setFont(new Font("標楷體", Font.BOLD, 36));
        btn3.addActionListener((e) -> {
            Page3();
        });
        btn3.setFont(new Font("標楷體", Font.BOLD, 36));
        btn4.addActionListener((e) -> {
            Page4();
        });
        btn4.setFont(new Font("標楷體", Font.BOLD, 36));
        btn5.addActionListener((e) -> {
            Page5();
        });
        btn5.setFont(new Font("標楷體", Font.BOLD, 36));
        btn6.addActionListener((e) -> {
            Page6();
        });
        btn6.setFont(new Font("標楷體", Font.BOLD, 36));
        btn1_.setFont(new Font("標楷體", Font.BOLD, 36));
        btn2_.setFont(new Font("標楷體", Font.BOLD, 36));
        btn3_.setFont(new Font("標楷體", Font.BOLD, 36));
        btn4_.setFont(new Font("標楷體", Font.BOLD, 36));
        btn5_.setFont(new Font("標楷體", Font.BOLD, 36));
        btn6_.setFont(new Font("標楷體", Font.BOLD, 36));
        btn7.addActionListener((e) -> {
            PageMain();
        });
        btn7.setFont(new Font("標楷體", Font.BOLD, 36));
        tf1.setFont(new Font("標楷體", Font.BOLD, 36));
        tf1.setHorizontalAlignment(JTextField.CENTER);
        tf2.setFont(new Font("標楷體", Font.BOLD, 36));
        tf2.setHorizontalAlignment(JTextField.CENTER);
    }

    static void Reset(String s) {
        lb.setText(s);
        pl.removeAll();
    }
    
    static String AccList() {
        String s = "";
        
        for (Client c : com.get(cb1.getSelectedIndex()).man) {
            s += c.name+"：<br>";
            for (Account a : c.acc) {
                s += "["+a.no+"]帳號餘額 "+a.amount+" 元<br>";
            }
            s += "<br>";
        }
        return s;
    }

    /*class JTextFieldFocus implements FocusListener {
        public void focusGained(java.awt.event.FocusEvent e) {
            if ( tf1.getText().equals("請輸入帳戶名稱") )
                tf1.setText("");
        }
        
        public void focusLost(java.awt.event.FocusEvent e) {
            if ( tf1.getText().equals("") )
                tf1.setText("請輸入帳戶名稱");
        }        
    }*/
    
}
