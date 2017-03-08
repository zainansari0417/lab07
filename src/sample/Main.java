package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    Map<String, Integer> boo = new HashMap<>();
    ArrayList<String> booKeys = new ArrayList<>();

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Canvas canvas = new Canvas();
        canvas.setWidth(600);
        canvas.setHeight(400);

        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/zain/csci2020u/lab07/weatherwarnings-2015.csv"));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] lineData = line.split(",");
                if(boo.containsKey(lineData[5])) {
                    int num = boo.get(lineData[5]);
                    boo.replace(lineData[5], num+1);
                } else {
                    boo.put(lineData[5], 1);
                    booKeys.add(lineData[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        draw(canvas, root);

        root.getChildren().add(canvas);
        primaryStage.setTitle("Lab 7");
        primaryStage.setScene(new Scene(root, 650, 500));
        primaryStage.show();
    }

    public void draw(Canvas canvas, Group root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        float runningAngle = 0;
        float currentAngle = 0;
        float runningTotal = 0;

        for(int i=0; i<boo.size(); i++) {
            runningTotal += boo.get(booKeys.get(i));
        }
        float total = runningTotal/360;

        for(int i=0; i<boo.size(); i++){
            gc.setFill(pieColours[i]);
            gc.setStroke(Color.BLACK);
            currentAngle = (float)(boo.get(booKeys.get(i)))/total;
            gc.fillArc(300,60,300,300,runningAngle,currentAngle, ArcType.ROUND);
            gc.strokeArc(300,60,300,300,runningAngle,currentAngle,ArcType.ROUND);
            runningAngle += currentAngle;
        }

        for(int i=0; i<boo.size(); i++) {
            gc.setFill(pieColours[i]);
            gc.fillRect(25, 50+(i*40), 40,30);
            gc.strokeRect(25,50+(i*40),40,30);
            Label label = new Label(booKeys.get(i));
            label.setTranslateX(80);
            label.setTranslateY(57+(i*40));
            root.getChildren().add(label);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
