package Ticket;

public class Main {

    static int[] price = {310, 290, 260, 280, 155};
    static String[] type = {"全　票-" + price[0], "優待票-" + price[1], "孩童票-" + price[2], "早場票-" + price[3], "愛心票-" + price[4]};
    static int[] num = new int[5];
    static int total;

    public static void main(String[] args) {
        Interface.Style();
    }
}
