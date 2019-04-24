package com.ypb.concurrent.stage2.chatper11;
/**
 * @className ExecutionTask
 * @description 需要执行的任务
 * @author yangpengbing
 * @date 22:46 2019/4/24
 * @version 1.0.0
 */
public class ExecutionTask implements Runnable {

    private BaseAction dbAction = new QueryFromDBAction();
    private BaseAction httpAction = new QueryFromHttpAction();

    @Override
    public void run() {
//        Context context = new Context();

        Context context = ActionContext.getActionContext().get();

        execute(context);
    }

    private void execute(Context context) {
        dbAction.execute(context);
        print("the name query successfully...");

        httpAction.execute(context);
        print("the card id query successfully...");

        print("the name is " + context.getName() + " and cardID is " + context.getCardID());
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
