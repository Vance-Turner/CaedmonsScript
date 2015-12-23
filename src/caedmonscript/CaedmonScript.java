/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caedmonscript;

import caedmonscript.ui.ScorePane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import scorelib.BasicScore;
import scorelib.IntervalMeasure;
import scorelib.IntervalNote;

/**
 *
 * @author Vance
 */
public class CaedmonScript extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        BasicScore score = new BasicScore();
        score.setScoreTitle("Test Music");
        IntervalMeasure measure = new IntervalMeasure();
        IntervalNote note = new IntervalNote();
        note.setInterval(3);
        measure.addNote(note, 0);
        score.addMeasure(measure, 0);
        ScorePane scorePane = new ScorePane(score);
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
        
        StackPane root = new StackPane();
        root.getChildren().add(scorePane);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
