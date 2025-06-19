package com.miramar_water_jets;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

public class FloatDataPlotter {
    @FXML private ScatterChart<Double, Double> scatterChart_1;

    private ArrayList<XYChart.Series<Double, Double>> graphs = new ArrayList<>();
    private int packetIdNum = 0;

    private BlockingQueue<DataPoint> queue = new LinkedBlockingQueue<>();

    @SuppressWarnings("exports")
    @FXML public TextArea textArea;

    @FXML
    public void initialize() {

        FloatDataReader fdr = new FloatDataReader(queue);
        Thread fdrThread = new Thread(fdr);
        fdrThread.start();

    }

    public void run() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                DataPoint dp;
                String lastPacketId = "";

                XYChart.Series<Double, Double> packet = null;

                while ((dp = queue.poll()) != null) {

                    if (!dp.getPacketId().equals(lastPacketId)) {
                        System.out.println("Creating DataSet " + packetIdNum);
                        packet = new XYChart.Series<>();
                        packet.setName("DataSet " + packetIdNum);
                        lastPacketId = dp.getPacketId();
                        if (!dp.getPacketId().equals("pkt0")) {
                            graphs.add(packet);
                            scatterChart_1.getData().add(packet);
                        }
                        textArea.setText(textArea.getText() + "\nDataSet " + packetIdNum
                                + "\n-----------------------------------\n");
                        packetIdNum++;
                    }

                    packet.getData().add(new XYChart.Data<>(dp.getTime(), dp.getDepth()));
                    textArea.setText(textArea.getText() + dp.toString() + "\n");
                }
            }
        };
        timer.start();
    }
}
