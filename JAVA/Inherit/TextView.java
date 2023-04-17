package Inherit;

public class TextView extends View {

    CharSequence text;

    TextView(int id, int width, int height) {
        super(id, width, height);	//呼叫父類別建構子
    }

    CharSequence getText() {
        return text;
    }

    void setText(CharSequence text) {
        this.text = text;
    }

    @Override	//覆寫父類別方法
    void show() {
        super.show();
        if (text == null) {
            System.out.println("");
        } else {
            System.out.println("text:" + text);
        }
    }

}
