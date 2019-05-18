package com.ypb.concurrent.stage3.chapter2;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchExample4 {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Event[] events = new Event[]{new Event(1), new Event(2)};

        ExecutorService pool = Executors.newFixedThreadPool(5);

        int batchSize = 10;
        for (Event event : events) {
            List<Table> tables = capture(event, batchSize);

            TaskGroup group = new TaskGroup(tables.size(), event);
            for (Table table : tables) {

                TaskBatch batch = new TaskBatch(group, new CountDownLatch(2));

                pool.execute(new RecordCountRunnable(table, batch));
                pool.execute(new ColumnSchemaRunnable(table, batch));
            }
        }

        pool.shutdown();
    }

    private static List<Table> capture(Event event, int batchSize) {
        List<Table> tables = Lists.newArrayList();
        for (int i = 0; i < batchSize; i++) {
            tables.add(new Table("table-" + event.getId() + "-" + i, i * 1000));
        }

        return tables;
    }

    private static class Event {

        @Getter
        private int id;

        private Event(int id) {
            this.id = id;
        }
    }

    private interface Watcher {

        void done(Table table);
    }

    private static class TaskGroup implements Watcher {

        private final CountDownLatch latch;
        private final Event event;

        private TaskGroup(int size, Event event) {
            this.latch = new CountDownLatch(size);
            this.event = event;
        }

        @Override
        public void done(Table table) {
            latch.countDown();
            if (latch.getCount() == 0) {
                System.out.println("all of table done in event [" + event.getId() + "]");
            }
        }
    }

    private static class TaskBatch implements Watcher {

        private final CountDownLatch latch;
        private final TaskGroup group;

        public TaskBatch(TaskGroup group, CountDownLatch latch) {
            this.latch = latch;
            this.group = group;
        }

        @Override
        public void done(Table table) {
            latch.countDown();

            if (latch.getCount() == 0) {
                System.out.println("the table " + table.tableName + " finish work , [" + table + "]");

                group.done(table);
            }
        }
    }

    @ToString
    private static class Table {
        private String tableName;
        private long sourceRecordCount = 10;
        private long targetRecordCount;
        private String sourceColumnSchema = "<table name='a'><column name='col1' type='varchar2'/></table>";
        private String targetColumnSchema = "";

        private Table(String tableName, long sourceRecordCount) {
            this.tableName = tableName;
            this.sourceRecordCount = sourceRecordCount;
        }
    }

    private static class RecordCountRunnable implements Runnable {
        private final Table table;
        private final TaskBatch batch;

        private RecordCountRunnable(Table table, TaskBatch batch) {
            this.table = table;
            this.batch = batch;
        }

        @Override
        public void run() {
            try {
                slowly(10000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }

            table.targetRecordCount = table.sourceRecordCount;
            // 完成操作，更新数据库
//            System.out.println("the table " + table.tableName + " target count capture done and update the data..");

            batch.done(table);
        }
    }

    private static void slowly(int mills) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(mills));
    }

    private static class ColumnSchemaRunnable implements Runnable {
        private final Table table;
        private final TaskBatch batch;

        public ColumnSchemaRunnable(Table table, TaskBatch batch) {
            this.table = table;
            this.batch = batch;
        }

        @Override
        public void run() {
            try {
                slowly(20000);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }

            table.targetColumnSchema = table.sourceColumnSchema;
            // 完成操作，更新数据库
//            System.out.println("the table " + table.tableName + " target column schema capture done and update the data..");

            batch.done(table);
        }
    }
}
