package com.ypb.concurrent.chapter1;
/**
 * @className TemplateMethod
 * @description 模版方法
 * @author yangpengbing
 * @date 15:05 2019/4/5
 * @version 1.0.0
 */
public class TemplateMethod {

    /**
     * 方法被定义成final，不能被重写
     * @param message
     */
    public final void print(String message) {
        System.out.println("##############");
        wrapPrint(message);
        System.out.println("##############");
    }

    protected void wrapPrint(String message) {

    }

    public static void main(String[] args) {
        TemplateMethod tm = new TemplateMethod(){

            @Override
            protected void wrapPrint(String message) {
                System.out.println("+" + message + "+");
            }
        };
        tm.print("Hello Thread");

        TemplateMethod tm2 = new TemplateMethod(){

            @Override
            protected void wrapPrint(String message) {
                System.out.println("*" + message + "*");
            }
        };
        tm2.print("Hello Thread");

    }
}
