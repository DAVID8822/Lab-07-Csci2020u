
package csci2020u.lab07;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import java.io.*;
import java.util.TreeMap;

public class Main extends Application {

    @FXML Canvas mainCanvas;
    @FXML GraphicsContext gc;

    private TreeMap<String, Integer> mapData = new TreeMap<>();
    private static Color[] colors = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.DARKSALMON
    };


    @FXML
    public void initialize() throws IOException {
        String fileName = "C:\\Users\\David\\IdeaProjects\\Project 1\\src\\csci2020u\\lab07\\weatherwarnings-2015.csv";
        gc = mainCanvas.getGraphicsContext2D();
        drawPieGraph(mapData,colors, new File(fileName));
        drawLegend(mapData,colors);
    }




    public void drawPieGraph(TreeMap<String,Integer> map, Color[] colors, File filename) throws IOException {
        double[] angle = new double[map.size()];
        double total = 0;
        double initAngle = 0.0;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");
            String type = columns[5];
            if (map.containsKey(type)) {
                int count = map.get(type);
                count += 1;
                map.replace(type, count); //Increase count
            } else {
                map.put(type, 1);
            }
        }
        reader.close();
        for (String key : map.keySet()) {
            total += map.get(key);
        }
        int i = 0;
        for (String key : map.keySet()) {
            angle[i] = ((double) map.get(key) / total) * 360;
            i++;
        }

        for (i =0; i < map.size(); i++) {
            gc.setFill(colors[i]);
            gc.fillArc(500,0.0,250,250,initAngle,angle[i], ArcType.ROUND);
            initAngle += angle[i];

        }
    }

    public void drawLegend(TreeMap<String, Integer> map, Color[] colors) {
        int y = 0,i = 0;
        for (String key : map.keySet()) {
            gc.fillText(key, 150, y + 20);
            gc.fillRect(0, y, 100, 30);
            gc.setFill(colors[i]);
            y += 50;
            i++;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab 7");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
