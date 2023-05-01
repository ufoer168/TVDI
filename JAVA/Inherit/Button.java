package Inherit;

public class Button extends TextView {

    Button(int id, int width, int height) {
        super(id, width, height);
    }

    void click() {
        System.out.println("----click----");
        System.out.println(this.getText() + " 被按下了");
    }
}