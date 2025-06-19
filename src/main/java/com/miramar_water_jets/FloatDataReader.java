package com.miramar_water_jets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

import com.fazecast.jSerialComm.SerialPort;

public class FloatDataReader implements Runnable {

    private final String COMM_PORT_WINDOWS = "COM3";
    private int baudRate = 115200;
    private String data_end_limiter = "&%";

    // microcontroller will stop after 3 seconds after not recieving data.
    private int timeToGetData = 3000; // milliseconds

    private BlockingQueue<DataPoint> queue;
    private SerialPort commPort;

    FloatDataReader(BlockingQueue<DataPoint> queue) {

        this.queue = queue;

        // setting comm port and baud rate
        commPort = SerialPort.getCommPort(COMM_PORT_WINDOWS);
        commPort.setBaudRate(baudRate);

        // TIMEOUT_NONBLOCKING
        // TIMEOUT_READ_SEMI_BLOCKING
        // TIMEOUT_READ_BLOCKING
        // commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, timeToGetData,
        // 0);
        commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
    }

    @Override
    public void run() {

        // run method called
        System.out.println("Running...");

        // check if connection is open
        // ;
        if (!commPort.openPort()) {
            System.out.println("Comm port is not open");
            return;
        }

        System.out.println("Port opened and running.");

        String dataline;
        int line = 1;

        try (BufferedReader datareader = new BufferedReader(new InputStreamReader(commPort.getInputStream()))) {
            // if (commPort.getInputStream().available() > 0) {
            while ((dataline = datareader.readLine()) != null) {
                String[] datapoint = dataline.split(",");

                if (dataline.startsWith(data_end_limiter))
                    break;

                if (!
                // (datapoint[0].startsWith("PN0") ||

                (datapoint[0].equals(data_end_limiter)))
                // )
                {

                    System.out.println(line + ": " + dataline);
                    if (!datapoint[0].startsWith("PN"))
                        continue;

                    queue.put(new DataPoint(line,
                            datapoint[0], Double.parseDouble(datapoint[1]),
                            Double.parseDouble(datapoint[2])));
                }

                line++;
            }
            // }
            datareader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        commPort.closePort();
        // }
    }
}
