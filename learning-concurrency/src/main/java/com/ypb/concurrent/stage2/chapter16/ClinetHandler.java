package com.ypb.concurrent.stage2.chapter16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClinetHandler implements Runnable {

    private final Socket socket;
    private volatile boolean running = true;

    public ClinetHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
        ) {
            while (running) {
                String message = br.readLine();
                if (Objects.isNull(message)) {
                    break;
                }

                printConsole(message);

                pw.write("echo " + message + "\n");
                pw.flush();
            }
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            running = false;
        } finally {
            stop();
        }
    }

    private void printConsole(String message) {
        System.out.println("come from client -> " + message);
    }

    public void stop() {
        if (!running) {
            return;
        }

        try {
            socket.close();
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        }
    }
}
