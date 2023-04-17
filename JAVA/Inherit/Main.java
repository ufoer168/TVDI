package Inherit;

public class Main {

    public static void main(String[] args) {
        TextView tv1;
        TextView tv2;
        Button btn;
        ViewGroup vg;
        
        tv1 = new TextView(1, 50, 20);
        tv1.setText("訊息");

        tv2 = new TextView(2, 50, 20);
        tv2.setText("請按OK");

        btn = new Button(3, 50, 20);
        btn.setText("OK");

        vg = new ViewGroup(4, 50, 60);
        vg.addView(tv1);
        vg.addView(tv2);
        vg.addView(btn);
        vg.show();

        Button b = (Button) vg.findViewById(3);
        b.click();
    }
    
}
