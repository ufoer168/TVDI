package WebView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Stock extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("盯股幫");
        primaryStage.setMaximized(true);

        WebView wv = new WebView();
        wv.getEngine().load("https://stock.tw-dvd.us/webview");

        AnchorPane ap = new AnchorPane();
        ap.getChildren().add(wv);
        AnchorPane.setTopAnchor(wv, 0.0);
        AnchorPane.setBottomAnchor(wv, 0.0);
        AnchorPane.setLeftAnchor(wv, 0.0);
        AnchorPane.setRightAnchor(wv, 0.0);

        Scene scene = new Scene(ap);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
