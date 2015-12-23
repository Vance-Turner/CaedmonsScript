/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caedmonscript.ui;

import java.util.ArrayList;
import java.util.UUID;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import scorelib.BasicScore;
import scorelib.IntervalMeasure;

/**
 * The pane responsible for displaying what can be thought of as the score: 
 * music title page along with the various bars that hold the measures of music.
 * @author Vance
 */
public class ScorePane extends Parent{
   
    /**
     * Top level layout pane
     */
    private BorderPane box0 = new BorderPane();
    
    /**
     * The pane for displaying the score title
     */
    private TitlePane titlePane;
    
    /**
     * Holds all the measures. We use a FlowPane to automatically flow the measures.
     */
    private FlowPane measureHolderPane = new FlowPane();
    
    /**
     * Holds the actual MeasurePanes as they are meant to be displayed.
     */
    private ArrayList<MeasurePane> measureList = new ArrayList<>();
    
    /**
     * Listens to changes in the list of measures in the BasicScore and updates
     * the UI accordingly.
     */
    private ListChangeListener<IntervalMeasure> measureListListener = new ListChangeListener<IntervalMeasure>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends IntervalMeasure> c) {
            measureHolderPane.getChildren().setAll(measureList);
        }
    };
    
    /**
     * The BasicScore that holds all data which we display.
     */
    private BasicScore score;
    
    public ScorePane(BasicScore initScore){
        this.score = initScore;
        buildUIComponent();
    }
    
    public void buildUIComponent(){
        titlePane = new TitlePane(score);
        box0.setTop(titlePane.getUI());
        box0.setCenter(measureHolderPane);
        // Now build measures based on the score
        score.getMeasures().addListener(measureListListener);
        // For each existing measure we create a UI component for displaying
        // that measure's data
        for(IntervalMeasure measure: score.getMeasures()){
            MeasurePane measurePane = new MeasurePane(measure);
            measureList.add(measurePane);
        }
        // Add all the known MeasurePanes into the box.
        measureHolderPane.getChildren().setAll(measureList);
        this.getChildren().add(box0);
    }
}
