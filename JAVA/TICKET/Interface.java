package Ticket;

import static Ticket.Main.*;
import java.awt.*;
import javax.swing.*;

public class Interface {

    static JFrame frm = new JFrame("TVDI - 影城購票系統");
    static JLabel lb1 = new JLabel("名偵探柯南");
    static Panel pl2 = new Panel();
    static Panel pl2_1 = new Panel();
    static Panel pl2_2 = new Panel();
    static JComboBox<String> ch1 = new JComboBox<>(type);
    static JComboBox<Integer> ch2 = new JComboBox<>();
    static JButton btn1 = new JButton("加入訂單");
    static JButton btn2 = new JButton("清除訂單");
    static Panel pl4 = new Panel();
    static Panel pl5 = new Panel();
    static JLabel lb4_1 = new JLabel("訂單明細");
    static JLabel lb4_2 = new JLabel();
    static JLabel lb5_1 = new JLabel("總計金額");
    static Label lb5_2 = new Label();

    static void Style() {

        frm.setSize(400, 600);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setLayout(new GridLayout(3, 1, 0, 5));

        pl2.setLayout(new GridLayout(3, 1));
        lb1.setHorizontalAlignment(JLabel.CENTER);
        lb1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl2.add(lb1);

        ch1.setFont(new Font("標楷體", Font.PLAIN, 24));
        pl2_1.add(ch1);

        for (int i = 1; i < 10; i++) {
            ch2.addItem(i);
        }
        ch2.setFont(new Font("標楷體", Font.PLAIN, 24));
        pl2_1.add(ch2);

        pl2.add(pl2_1);

        btn1.addActionListener(e -> {
            String list = "";

            num[ch1.getSelectedIndex()] += ch2.getSelectedIndex() + 1;

            for (int i = 0; i < type.length; i++) {
                if (num[i] > 0) {
                    list += type[i] + " x " + num[i] + " = " + (price[i] * num[i]) + "<br>";
                }
            }

            lb4_2.setText("<html>" + list + "</html>");

            total += price[ch1.getSelectedIndex()] * (ch2.getSelectedIndex() + 1);
            lb5_2.setText(Integer.toString(total));
        });
        btn1.setFont(new Font("標楷體", Font.PLAIN, 24));
        pl2_2.add(btn1);

        btn2.addActionListener(e -> {
            for (int i = 0; i < type.length; i++) {
                num[i] = 0;
            }
            lb4_2.setText("");

            total = 0;
            lb5_2.setText("");
        });
        btn2.setFont(new Font("標楷體", Font.PLAIN, 24));
        pl2_2.add(btn2);

        pl2.add(pl2_2);
        frm.add(pl2);

        pl4.setLayout(new GridLayout(2, 1));
        pl4.add(lb4_1);
        lb4_1.setHorizontalAlignment(JLabel.CENTER);
        lb4_1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl4.add(lb4_2);
        lb4_2.setHorizontalAlignment(JLabel.CENTER);
        lb4_2.setFont(new Font("標楷體", Font.PLAIN, 14));
        lb4_2.setForeground(Color.BLUE);
        frm.add(pl4);

        pl5.setLayout(new GridLayout(2, 1));
        pl5.add(lb5_1);
        lb5_1.setHorizontalAlignment(JLabel.CENTER);
        lb5_1.setFont(new Font("標楷體", Font.BOLD, 36));
        pl5.add(lb5_2);
        lb5_2.setAlignment(Label.CENTER);
        lb5_2.setFont(new Font("標楷體", Font.PLAIN, 36));
        lb5_2.setForeground(Color.BLUE);
        frm.add(pl5);

        frm.setVisible(true);
    }

}
