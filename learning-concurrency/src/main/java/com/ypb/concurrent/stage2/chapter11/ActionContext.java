package com.ypb.concurrent.stage2.chapter11;

public class ActionContext {

    private static final ThreadLocal<Context> threadLocal = new ThreadLocal<Context>() {

        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    private ActionContext(){}

    private static class ActionContextHolder {
        private static final ActionContext context = new ActionContext();
    }

    public static ActionContext getActionContext() {
        return ActionContextHolder.context;
    }

    public Context get() {
        return threadLocal.get();
    }
}
