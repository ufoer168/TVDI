package javaapp;

import java.util.Scanner;

class JavaApp {

    public static void main(String[] args) {
        /*System.out.println("輸入成績後計算總分、平均（多筆以,分隔）");
        Calculate.Score();
        
        System.out.println("溫度換算（1.攝氏->華氏，2.華氏->攝氏）");
        Calculate.Temperature();
        
        System.out.println("帳戶餘額計算（正為存款、負為提款）");
        Calculate.Balance();

        System.out.println("閏年判斷");
        Calculate.Leapyear();
        
        System.out.println("隨機產生成績，計算總分與平均，並顯示評語");
        Random.Score();
    
        System.out.println("電腦隨機解碼");
        Random.Guess();

        System.out.println("日期格式判斷");
        Calculate D = new Calculate();
        System.out.println(D.CheckDate());*/

        System.out.println("銀行作業（1.開戶、2.存取款）");
        Bank();
    }

    static void Bank() {
        Scanner sc = new Scanner(System.in);
        Constructor ca;
        //Constructor[] acc = new Constructor[9];
        //int a = 0;
        boolean ok;

        System.out.print("功能：");
        while (sc.hasNext()) {
            switch (sc.next()) {
                case "1":
                    ca = new Constructor();
                    if (ca.CreateAcc()) {
                        Global.acc.add(ca);
                    }
                    //acc[a] = new Constructor();
                    //if ( acc[a].CreateAcc() )
                    //    a++;

                    break;

                case "2":
                    ok = false;

                    System.out.print("帳戶名稱：");
                    String fn = sc.next();
                    for (int i = 0; i < Global.acc.size(); i++) {
                        if (Global.acc.get(i).n.equals(fn)) {
                            ok = Global.acc.get(i).FindAcc();
                        }
                    }
                    /*for (int i = 0; i < a; i++) {
                        if ( acc[i].n.equals(fn) ) {
                            ok = acc[i].FindAcc();                            
                        }
                    }*/

                    if (!ok) {
                        System.out.println("查無該帳戶");
                    }

                    break;

                default:
                    System.out.println("代號錯誤，請重新輸入");
            }

            System.out.println();
            System.out.print("功能：");
        }
    }

}
