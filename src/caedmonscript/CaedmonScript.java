/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caedmonscript;

import caedmonscript.ui.MeasurePanel;
import caedmonscript.ui.ScorePane;
import java.awt.FlowLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
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
        note.setDuration(1);
        measure.addNote(note, 16);
        measure.getSignature().setBeatCount(4);
        measure.getSignature().setBaseUnit(1);
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
        
        // Test the frame
        JFrame testFrame = new JFrame("Test Measure Pane");
        testFrame.setLayout(new FlowLayout());
        MeasurePanel testPanel = new MeasurePanel();
        testPanel.setMeasure(measure);
        testFrame.add(testPanel);
        testFrame.pack();
        testFrame.setDefaultCloseOperation(testFrame.EXIT_ON_CLOSE);
        testFrame.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
