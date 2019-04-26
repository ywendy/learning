package com.ypb.concurrent.stage2.chapter12;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class BalkingData {

    private final String fileName;
    private String context;
    private boolean changed;

    public BalkingData(String fileName, String context) {
        this.fileName = fileName;
        this.context = context;
        this.changed = true;
    }

    public synchronized void change(String context) {
        this.context = context;
        this.changed = true;
    }

    public synchronized void save() throws IOException {
        if (!changed) {
            return;
        }

        doSave();
        changed = false;
    }

    private void doSave() throws IOException {
        printConsole(context);
        Writer writer = new FileWriter(fileName, true);
        writer.write(context);
        writer.write("\n");
        writer.flush();
    }

    private void printConsole(String context) {
        System.out.println(Thread.currentThread().getName() + " calls do save, context=" + context);
    }
}
