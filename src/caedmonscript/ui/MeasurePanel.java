/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caedmonscript.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import scorelib.IntervalMeasure;
import scorelib.IntervalNote;
import scorelib.IntervalSignature;
import scorelib.Universal;

/**
 * Displays using interval notation the notes provided in a single IntervalMeasure.
 * @author vance
 */
public class MeasurePanel extends JPanel {
    
    IntervalMeasure mMeasure;
    IntervalSignature mSignature = new IntervalSignature();
    boolean showSignature = false;
    
    /**
     * Draws a "quarter" note, a note representing one beat.
     */
    private final NotePainter notePainter_1 = new BasicNotePainter() {

        @Override
        public void doDraw(Graphics2D graphics, float noteX, float noteY, float minWidth, float minHeight) {
            graphics.setColor(Color.black);
            graphics.fillOval((int)(noteX-(minWidth/2.0f)), (int)(noteY-(minHeight/2.0f)), (int)minWidth, (int)minHeight);
        }
    };
    
    public MeasurePanel(){
        int btct = caedmonscript.Universal.DEFAULT_BEAT_COUNT;
        int ntwdth = caedmonscript.Universal.NOTE_WIDTH;
        int ntheight = caedmonscript.Universal.NOTE_HEIGHT;
        // Create blank measure data
        mMeasure = new IntervalMeasure();
        mMeasure.getSignature().setBaseUnit(4);
        mMeasure.getSignature().setBeatCount(btct);
        mMeasure.getSignature().setKey(1);
        setPreferredSize(new Dimension(btct*16*ntwdth, (Universal.INTERVAL_LINES*2+1)*ntheight));
        setBorder(new LineBorder(Color.yellow, 20));
    }
    
    public void setMeasure(IntervalMeasure newMeasure){
        mMeasure = newMeasure;
        mSignature = mMeasure.getSignature();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        // Draw the interval lines
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.black);
        for(int i=0; i<4; i++){
            float yi = (caedmonscript.Universal.NOTE_HEIGHT*(i+1))+(caedmonscript.Universal.MEASURE_LINE_HEIGHT*i);
            g2d.drawLine(0, (int)yi, (int)caedmonscript.Universal.NOTE_WIDTH*mSignature.getBeatCount()*16, (int)yi);
        }
        // Now we draw the lines
        if(showSignature){
            
        }else{
            int noteWidth = caedmonscript.Universal.NOTE_WIDTH;
            int noteHeight = caedmonscript.Universal.NOTE_HEIGHT;
            for(int i=0; i<mSignature.getBeatCount()*16; i++){
                List<IntervalNote> notes = mMeasure.getNotesByPosition(i);
                for(int j=0; j<notes.size(); j++){
                    System.out.println("Got some notes for time position>"+i);
                    IntervalNote note = notes.get(j);
                    float noteX = (noteWidth/2.0f)+i*noteWidth;
                    int noteIndex = (Universal.INTERVAL_LINES*2-1+2)-note.getInterval();
                    float noteY = noteIndex*noteWidth/2.0f;
                    System.out.println("Got note position>"+noteX+" "+noteY);
                    switch(note.getDuration()){
                        case 1:
                        System.out.println("Drawing quarter note...");
                            notePainter_1.doDraw(g2d, noteX, noteY, noteWidth, noteWidth);
                    }
                }
            }
        }
    }
    
    /**
     * Used to draw a specific type of note
     */
    public interface NotePainter {
        
        /**
         * 
         * @param graphics Used to draw the note
         * @param noteX The x coordinate of the note's center
         * @param noteY The y coordinate of the note's center
         * @param minWidth The minimum width the note should be
         * @param minHeight The minimum height the note should be.
         */
        public void doDraw(Graphics2D graphics,
                float noteX, float noteY,
                float minWidth,float minHeight);
        
        public void setNote(IntervalNote note);
        
    }
    
    abstract class BasicNotePainter implements NotePainter {
        
        private IntervalNote note;
        
        public void setNote(IntervalNote newNote){
            note = newNote;
        }
    }
}
