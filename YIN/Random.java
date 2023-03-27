package javaapp;
import java.util.*;

public class Random {
    
    //隨機產生成績，計算總分與平均，並顯示評語
    static void Score() {
        int[] s = new int[2];
        s[0] = (int)(Math.random() * 100);
        s[1] = (int)(Math.random() * 100);
        
        System.out.println("英文：" + s[0]);
        System.out.println("數學：" + s[1]);
        System.out.println("總和：" + Calculate.Sum(s));
        double a = Calculate.Average(s);
        System.out.println("平均：" + a);            
        
        if( a >= 90.0 )
            System.out.println("評語：超棒");
        else if( a >= 80.0 )
            System.out.println("評語：很棒");
        else if( a >= 70.0 )
            System.out.println("評語：還行");
        else
            System.out.println("評語：加油");
        System.out.println();
    }
    
    //帳戶餘額計算
    static void Bank() {
        Scanner sc = new Scanner(System.in);
        int b = 5000;
        int t;
        
        System.out.println("帳戶餘額：5,000元");
        System.out.print("操作：");
        while (sc.hasNextInt()) {
            t = sc.nextInt();
            b += t;
            if ( b >= 0 ) {
                System.out.println(( t > 0 ? "存款" : "提款")+Math.abs(t)+"元，餘額"+b+"元");
                System.out.print("操作：");
            }
            else {
                System.out.println("提款"+Math.abs(t)+"元，餘額不足"+b+"元");
                b -= t;
            }
        }
        System.out.println();
    }
    
    //電腦隨機解碼
    static void Guess() {
        Scanner sc = new Scanner(System.in);        
        int i = 0;        
       
        while ( i < 1 || 9 < i ) {
            System.out.print("請輸入1~9的數字：");
            if( sc.hasNextInt() )
                i = sc.nextInt();
            else {
                System.out.println("輸入資料錯誤");
                break;
            }
        }
        
        if( 0 < i && i < 10 )
        {
            int j = 1;
            int g = (int)(Math.random() * 9) + 1;
            String gg = "";
            
            while ( g != i ) {
                System.out.println("電腦第" + j++ + "次猜[" + g + "]，不符合");
                gg += String.valueOf(g);
                g = (int)(Math.random() * 9) + 1;
                while ( gg.indexOf(String.valueOf(g)) != -1 )
                    g = (int)(Math.random() * 9) + 1;
            }
            System.out.println("電腦第" + j + "次猜[" + g + "]，符合");
        }
        System.out.println();
    }
    
}