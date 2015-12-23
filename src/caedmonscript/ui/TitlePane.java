/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caedmonscript.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import scorelib.BasicScore;

/**
 * The TitlePane displays the title of a piece of music.
 * @author Vance
 */
public class TitlePane {
    
    /**
     * Provides score information for us to display.
     */
    private BasicScore score;
    
    /**
     * The graphical element that displays the score title.
     */
    private Label scoreTitleLabel = new Label("Score Title");
    
    /**
     * The actual UI element to display the title pane.
     */
    private BorderPane titlePaneUI = new BorderPane(scoreTitleLabel);
    
    public TitlePane(BasicScore initScore){
        this.score = initScore;
        this.scoreTitleLabel.textProperty().bind(score.getScoreTitleProperty());
    }
    
    /**
     * Get the UI for use.
     * @return 
     */
    public Parent getUI(){
        return this.titlePaneUI;
    }
}
