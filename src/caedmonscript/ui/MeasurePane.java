/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caedmonscript.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import scorelib.IntervalMeasure;
import scorelib.IntervalNote;
import scorelib.IntervalSignature;

/**
 * A MeasurePane represents the notes in a single {@link IntervalMeasure} in
 * a graphical way. It depicts the notes in the measure through Interval Notation
 * style. 
 * @author Vance
 */
public class MeasurePane extends Parent {

    /**
     * The measure holding the notes we wish to show graphically.
     */
    protected IntervalMeasure measure;
    
    protected ObjectPropertyBase<IntervalMeasure> measureProperty;
    
    /**
     * Listens to any changes in the signature of our measure
     */
    private SignatureListener sigListener = new SignatureListener();
    
    /**
     * Listens to changes in the notes contained within the measure.
     */
    private MeasureNotesListener noteListListener = new MeasureNotesListener();

    /**
     * A list holding the graphical elements we use to represent notes within
     * the measure.
     */
    private ArrayList<NoteElement> noteElements = new ArrayList();
    
    /**
     * The graphical element used to display the signature of this measure. Not
     * always visible.
     */
    private SignatureElement sigElement = new SignatureElement();
    
    /**
     * We will have the measure lines visually behind the measure notes, thus we
     * use a StackPane to achieve z-ordering.
     */
    private StackPane basePane = new StackPane();
    
    /**
     * Holds the element for the signature and the notes
     */
    private HBox hpane = new HBox();
    
    /**
     * This allows us to easily space out our measure lines.
     */
    private VBox measureLinePane = new VBox();
    
    /**
     * This holds the actual notes we wish to display. The GridPane allows easy
     * visual layout of the notes.
     */
    private GridPane notesPane = new GridPane();

    // The "lines" on the measure
    private Line line0;
    private Line line1;
    private Line line2;
    private Line line3;

    private IntegerProperty measureWidth = new SimpleIntegerProperty(80);
    private IntegerProperty measureHeight = new SimpleIntegerProperty(50);

    public MeasurePane(IntervalMeasure measure) {
        this.measure = measure;
        measureProperty = new SimpleObjectProperty<>(measure);
        // Set bindings for the measure itself
        measure.getNotesProperty().addListener(noteListListener);
        // Set bindings for the signature change
        IntervalSignature sig = measure.getSignature();
        sig.getBaseUnitProperty().addListener(sigListener);
        sig.getBeatCountProperty().addListener(sigListener);
        sig.getKeyProperty().addListener(sigListener);
        // Set bindings for changes on individual note properties within this measure
        Iterator<IntervalNote> it = measure.getNotesProperty().iterator();
        while (it.hasNext()) {
            IntervalNote note = it.next();
            NoteListener listener = new NoteListener(note);
            note.getDurationProperty().addListener(listener);
            note.getIntervalProperty().addListener(listener);
        }
        buildUI();
    }

    class NoteListener implements ChangeListener<Number> {

        private IntervalNote parent;

        public NoteListener(IntervalNote note) {
            parent = note;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            NoteElement noteEle = null;
            for (int i = 0; i < noteElements.size(); i++) {
                NoteElement temp = noteElements.get(i);
                if (temp.noteID.compareTo(parent.getID()) == 0) {
                    noteEle = temp;
                }
            }
            if (parent.getDurationProperty() == observable) {
                noteEle.changeDuration(newValue.intValue());
            } else {
                notesPane.getChildren().remove(noteEle.getGraphic());
                int timePos=measure.getNotePosition(parent);
                notesPane.add(noteEle.getGraphic(), timePos, parent.getInterval());
            }
        }

    }

    class MeasureNotesListener implements ListChangeListener<IntervalNote> {

        @Override
        public void onChanged(Change<? extends IntervalNote> c) {
            if(c.wasAdded()){
                for(IntervalNote note: c.getAddedSubList()){
                    NoteElement noteEle = new NoteElement(note.getID());
                    noteEle.changeDuration(note.getDuration());
                    int timePos = measure.getNotePosition(note);
                    notesPane.add(noteEle.getGraphic(), timePos, note.getInterval());
                }
            }else if(c.wasRemoved()){
                
            }
        }

    }

    class SignatureListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    class NoteElement {

        public Circle noteGraphic = new Circle(5);
        public UUID noteID;
        
        public NoteElement(UUID id){
            noteID = id;
        }

        public void changeDuration(int newDuration) {

        }
        
        public Shape getGraphic(){
            return noteGraphic;
        }
    }

    class SignatureElement {

        public FlowPane flowPane = new FlowPane();
        public Text beatCountTxt = new Text("4");
        public Text majorUnitTxt = new Text("4");
        public Text keyTxt = new Text("C");

        public void buildUI() {
            flowPane.getChildren().addAll(beatCountTxt, keyTxt, majorUnitTxt);
        }
    }

    public void buildUI() {
        line0 = new Line(0, 0, measureWidth.get(), 0);
        line1 = new Line(0, 0, measureWidth.get(), 0);
        line2 = new Line(0, 0, measureWidth.get(), 0);
        line3 = new Line(0, 0, measureWidth.get(), 0);

        measureLinePane.getChildren().addAll(line0, line1, line2, line3);
        measureLinePane.setSpacing(20);
        sigElement.buildUI();
        hpane.getChildren().addAll(sigElement.flowPane, notesPane);
        basePane.getChildren().addAll(measureLinePane, hpane);
        this.getChildren().add(basePane);
    }
}
