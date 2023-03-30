package javaapp;
import java.util.*;

class Calculate {
    static Scanner sc = new Scanner(System.in);
    
    //輸入成績後計算總分、平均
    static void Score() {
        Scanner ssc = new Scanner(System.in).useDelimiter(",");
        int i = 0;
        int[] s = new int[99];
        
        System.out.print("成績：");
        while ( ssc.hasNextInt() ) {
            s[i++] = ssc.nextInt();
        }
        
        int[] ss = new int[i];
        for (int j = 0; j < i; j++) {
            ss[j] = s[j];
        }
        System.out.println("總和：" + Sum(ss));
        System.out.println("平均：" + Average(ss));
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
        return (double)Calculate.Sum(s) / s.length;
    }
    
    //溫度換算
    static void Temperature() {
        int t;
        
        System.out.print("模式：");
        while ( sc.hasNextInt() ) {
            t = sc.nextInt();
            
            switch ( t ) {
                case 1:
                    System.out.print("攝氏：");
                    System.out.println("華氏：" + (sc.nextFloat() * 9 / 5 + 32));
                    break;
                    
                case 2:
                    System.out.print("華氏：");
                    System.out.println("攝氏：" + ((sc.nextFloat() - 32)) * 5 / 9);
                    break;
                    
                default:
                    System.out.println("輸入代號錯誤");
            }
            
            System.out.println();
            System.out.print("模式：");
        }
        System.out.println();
    }
    
    //帳戶餘額計算
    static void Bank() {
        int b = 5000;
        int t;
        
        System.out.println("帳戶餘額：5,000元");
        System.out.print("操作：");
        while ( sc.hasNextInt() ) {
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
    
    //閏年判斷
    static void Leapyear() {
        int y;
                
        System.out.print("西元年：");
        while ( sc.hasNextInt() ) {
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
    
    //日期格式判斷
    String CheckDate() {
        int y, m, d;
        String s = "";
        
        System.out.print("年月日（以空白分隔）：");
        y = sc.nextInt();
        m = sc.nextInt();
        d = sc.nextInt();
        
        if ( y < 1 )
            s = "年度";
        
        if ( m < 1 || m > 12 )
            s += ( s.isEmpty() ? "" : "、" )+"月份";
        
        if ( d < 1 || d > 30 )
            s += ( s.isEmpty() ? "" : "、" )+"日期";
        
        if ( s.isEmpty() )
            return "格式符合";
        else
            return s+"格式錯誤";
    }
    
}