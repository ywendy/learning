package com.ypb.concurrent.stage2.chapter4;

import com.google.common.collect.Lists;

import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @className Subject
 * @description 定义感兴趣的主题
 * @author yangpengbing
 * @date 22:55 2019/4/20
 * @version 1.0.0
 */
@Slf4j
public class Subject {

    private List<Observe> observes = Lists.newArrayList();

    /**
     * 状态
     */
    @Getter
    private int status;

    public void setStatus(int status) {
        if (status == this.status) {
            return;
        }

        this.status = status;
        notifyAllObserve();
    }

    public void attach(Observe observe) {
        observes.add(observe);
    }

    private void notifyAllObserve() {
        observes.stream().forEach(Observe::update);
    }

}
