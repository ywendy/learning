package com.ypb.concurrent.stage2.chatper11;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryFromHttpAction extends AbstractBaseAction {

    @Override
    public void execute(Context context) {
        String name = context.getName();

        try {
            slowly(2000);

            String cardID = "18210218321_" + Thread.currentThread().getId();

            context.setCardID(cardID);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
        }
    }

    @Override
    public void execute() {
        Context context = ActionContext.getActionContext().get();

        execute(context);
    }
}
