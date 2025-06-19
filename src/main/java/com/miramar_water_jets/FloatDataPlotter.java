package com.miramar_water_jets;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class FloatDataPlotter {
    @FXML private ScatterChart<Double, Double> scatterChart_1;

    private ArrayList<XYChart.Series<Double, Double>> graphs = new ArrayList<>();
    private int packetIdNum = 0;

    // this contains the packet data
    // private XYChart.Series<Double, Double> series_1 = new XYChart.Series<>();

    private BlockingQueue<DataPoint> queue = new LinkedBlockingQueue<>();

    @FXML public ListView<DataLabel> listView;
    @SuppressWarnings("exports")
    @FXML public TextArea textArea;

    @FXML
    public void initialize() {

        // scatterChart_1.getData().add(series_1);
        // series_1.setName("DataSeries "+);

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

                // System.out.println("Creating DataSet " + packetIdNum);
                // packet = new XYChart.Series<>();
                // packet.setName("DataSet " + packetIdNum);
                // // lastPacketId = dp.getPacketId();
                // graphs.add(packet);
                // scatterChart_1.getData().add(packet);
                // textArea.setText(textArea.getText() + "\nDataSet " + packetIdNum
                // + "\n-----------------------------------\n");
                // packetIdNum++;

                while ((dp = queue.poll()) != null) {
                    // series_1.getData().add(new XYChart.Data<>(dp.getTime(), dp.getDepth()));

                    if (!dp.getPacketId().equals(lastPacketId)) {
                        System.out.println("Creating DataSet " + packetIdNum);
                        packet = new XYChart.Series<>();
                        packet.setName("DataSet " + packetIdNum);
                        lastPacketId = dp.getPacketId();
                        graphs.add(packet);
                        scatterChart_1.getData().add(packet);
                        textArea.setText(textArea.getText() + "\nDataSet " + packetIdNum
                                + "\n-----------------------------------\n");
                        packetIdNum++;
                    }

                    packet.getData().add(new XYChart.Data<>(dp.getTime(), dp.getDepth()));
                    textArea.setText(textArea.getText() + dp.getPacketId() + " :: " + dp.toString() + "\n");
                    System.out.println(dp.getId() + ": " + dp);

                }
            }
        };
        timer.start();
    }
}
