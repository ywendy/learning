package com.ypb.concurrent.stage2.chapter16;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppServer extends Thread {

    private int port;
    private static final int DEFAULT_PORT = 12722;
    private volatile boolean start = true;
    private ServerSocket serverSocket;
    private List<ClinetHandler> handlers;
    private final ExecutorService pool;

    public AppServer() {
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.port = port;
        this.handlers = Lists.newLinkedList();
        this.pool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(port);
            while (start) {
                Socket socket = serverSocket.accept();

                ClinetHandler handler = new ClinetHandler(socket);
                handlers.add(handler);

                pool.submit(handler);
            }
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        } finally {
            destroyServerSocket();
        }
    }

    private void destroyServerSocket() {
        handlers.forEach(ClinetHandler::stop);
    }

    public void shutdown() throws IOException {
        start = false;
        this.interrupt();
        pool.shutdown();
        serverSocket.close();
    }
}
