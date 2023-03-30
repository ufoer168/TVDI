package javaapp;

class JavaApp {
    
    public static void main(String[] args) {
        /*System.out.println("輸入成績後計算總分、平均（多筆以,分隔）");
        Calculate.Score();
        
        System.out.println("溫度換算（1.攝氏->華氏，2.華氏->攝氏）");
        Calculate.Temperature();
        
        System.out.println("帳戶餘額計算（正為存款，負為提款）");
        Calculate.Bank();

        System.out.println("閏年判斷");
        Calculate.Leapyear();
        
        System.out.println("隨機產生成績，計算總分與平均，並顯示評語");
        Random.Score();
    
        System.out.println("電腦隨機解碼");
        Random.Guess();*/

        System.out.println("日期格式判斷");
        Calculate D = new Calculate();
        System.out.println(D.CheckDate());
    }    
    
}