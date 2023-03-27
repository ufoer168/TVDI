package javaapp;
import java.util.*;

public class Calculate {
    
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
    
    static void Fahrenheit() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("攝氏：");
        System.out.println("華氏：" + (sc.nextFloat() * 9 / 5 + 32));
        System.out.println();
    }
    
}