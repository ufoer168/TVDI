package javaapp;
import java.util.*;

class Calculate {
    
    //輸入成績後計算總分、平均（多筆以,分隔）
    static void Score() {
        Scanner sc = new Scanner(System.in).useDelimiter(",");
        int i = 0;
        int[] s = new int[99];
        
        System.out.print("成績：");
        while (sc.hasNextInt()) {
            s[i++] = sc.nextInt();
        }
        
        int[] ss = new int[i];
        for (int j = 0; j < i; j++) {
            ss[j] = s[j];
        }
        System.out.println("總和：" + Calculate.Sum(ss));
        System.out.println("平均：" + Calculate.Average(ss));
        System.out.println();
    }
    
    static int Sum(int[] s) {
        int all = 0;
        
        for (int j = 0; j < s.length; j++) {
            all = all + s[j];
        }
        return all;
    }
    
    static double Average(int[] s) {
        return Calculate.Sum(s) / 1.0 / s.length;
    }
    
    //溫度換算（1.攝氏->華氏，2.華氏->攝氏）
    static void Temperature() {
        Scanner sc = new Scanner(System.in);
        int t;
        
        System.out.print("模式：");
        while (sc.hasNextInt()) {
            t = sc.nextInt();
            
            if ( t == 1 ) {
                System.out.print("攝氏：");
                System.out.println("華氏：" + (sc.nextFloat() * 9 / 5 + 32));
            }
            else if ( t == 2 ) {
                System.out.print("華氏：");
                System.out.println("攝氏：" + ((sc.nextFloat() - 32)) * 5 / 9);
            }
            else
                System.out.print("輸入代號錯誤");
            
            System.out.println();
            System.out.print("模式：");
        }
        System.out.println();
    }
    
    //閏年判斷
    static void Leapyear() {
        Scanner sc = new Scanner(System.in);
        int y;
                
        System.out.print("西元年：");
        while (sc.hasNextInt()) {
            y = sc.nextInt();
            
            if ( y % 4 == 0 && y % 100 != 0 || y % 400 == 0 )
                System.out.println("閏年");
            else
                System.out.println("平年");
            
            System.out.println();
            System.out.print("西元年：");
        }
        System.out.println();
    }
}