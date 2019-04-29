package com.ypb.concurrent.stage2.chapter18;

public class Test {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();

        int times = 3;
        for (int i = 0; i < times; i++) {
            new MarkingStringClientThread("ypb" + i, activeObject).start();
        }

        times = 1;
        for (int i = 0; i < times; i++) {
            new DisplayStringClientThread("bgg" + i, activeObject).start();
        }
    }
}
