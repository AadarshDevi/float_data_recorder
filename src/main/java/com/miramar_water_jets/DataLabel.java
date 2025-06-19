package com.miramar_water_jets;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;

public class DataLabel extends BorderPane {

    // shows the time and depth.
    private Label timeLabel;
    private Label depthLabel;
    private Label dividerLabel;

    public DataLabel() {

        // create label
        timeLabel = new Label("Time(s)");
        depthLabel = new Label("Depth(m)");
        dividerLabel = new Label("|");

        setLeft(timeLabel);
        setCenter(dividerLabel);
        setRight(depthLabel);

        // set styles
        setCenterText(timeLabel, depthLabel, dividerLabel);

        this.requestFocus();
    }

    public DataLabel(double time, double depth) {

        // create label
        timeLabel = new Label(time + "s");
        depthLabel = new Label(depth + "m");

        // set styles
        setCenterText(timeLabel, depthLabel, dividerLabel);

        this.requestFocus();
    }

    public DataLabel(DataPoint dp) {

        // create label
        timeLabel = new Label(dp.getTime() + "s");
        depthLabel = new Label(dp.getDepth() + "m");

        // set styles
        setCenterText(timeLabel, depthLabel, dividerLabel);

        this.requestFocus();
    }

    public void setCenterText(@SuppressWarnings("exports") Label... labels) {
        for (Label label : labels) {
            if (label == null)
                System.out.println("Null label");
            else
                label.setTextAlignment(TextAlignment.CENTER);
        }
    }

    // @Override
    // public String toString() {
    // return timeLabel.getText() + "s | " + depthLabel.getText() + "m";
    // }
}
