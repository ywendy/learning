package com.ypb.concurrent.stage2.chapter11;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryFromDBAction extends AbstractBaseAction {

    @Override
    public void execute(Context context) {
        try {
            slowly(1000);
            String name = "yangpengbing_" + Thread.currentThread().getName();

            context.setName(name);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }
    }

    @Override
    public void execute() {
        try {
            slowly(1000);
            String name = "yangpengbing_" + Thread.currentThread().getName();

            ActionContext.getActionContext().get().setName(name);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }
    }
}
