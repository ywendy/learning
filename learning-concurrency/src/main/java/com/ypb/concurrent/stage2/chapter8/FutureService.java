package com.ypb.concurrent.stage2.chapter8;

import java.util.function.Consumer;

public class FutureService {

    public Future submit(FutureTask task) {
        SyncFutrue future = new SyncFutrue();

        new Thread(()->{
            Object call = task.call();
            future.done(call);
        }).start();

        return future;
    }

    public <T> Future submit(FutureTask task, final Consumer<T> callback) {
        SyncFutrue<T> futrue = new SyncFutrue<>();
        new Thread(() -> {
            Object call = task.call();
            futrue.done((T) call);
            callback.accept((T) call);
        }).start();

        return futrue;
    }
}
