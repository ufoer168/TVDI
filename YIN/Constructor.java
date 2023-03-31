package javaapp;
import java.util.Scanner;

class Constructor {
    static Scanner sc = new Scanner(System.in);
    int b = 0;
    String n = "";
    
    boolean CreateAcc() {
        System.out.print("帳戶名稱：");
        n = sc.next();
        
        for ( int i = 0; i < Global.acc.size(); i++ ) {
            if( Global.acc.get(i).n.equals(n) ) {
                System.out.println("帳戶重複");      
                return false;
            }
        }
        
        System.out.print("開戶金額：");
        b = sc.nextInt();
        
        if ( !n.isEmpty() && b > 0 ) {
            System.out.println("已完成「"+n+"」開戶，並存入 "+b+" 元");
            return true;
        }
        else {
            System.out.println("開戶失敗");      
            return false;
        }      
    }
    
    boolean FindAcc() {
        int fb;
        
        System.out.println("帳戶餘額 "+b+" 元");
        System.out.print("存取款（正為存款、負為提款）：");
        if ( sc.hasNextInt() ) {
            fb = sc.nextInt();
            b += fb;
            if ( b >= 0 ) {
                System.out.println(( fb > 0 ? "存款" : "提款")+Math.abs(fb)+"元，餘額"+b+"元");
            }
            else {
                System.out.println("提款"+Math.abs(fb)+"元，餘額不足"+b+"元");
                b -= fb;
            }
            return true;
        }
        else
            return false;
    }
}
