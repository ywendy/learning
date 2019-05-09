package com.ypb.classloader.chapter4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * @className EncryptUtils
 * @description 加密和解密的工具类
 * @author yangpengbing
 * @date 23:25 2019/5/9
 * @version 1.0.0
 */
@Slf4j
public final class EncryptUtils {

    public static final byte ENCRYPT_FACTOR = (byte) 0xff;

    private EncryptUtils(){}

    public static void doEncrypt(String source, String target) {
        try (FileOutputStream fos = new FileOutputStream(target);
             FileInputStream fis = new FileInputStream(source)) {
            int data;
            while ((data = fis.read()) != -1) {
                fos.write(data ^ ENCRYPT_FACTOR);
            }
            fos.flush();
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        String source = "E:\\temp\\customclassloader\\classloader4\\com\\ypb\\classloader\\chapter3\\MyObject.class";
        String target = "E:\\temp\\customclassloader\\classloader4\\com\\ypb\\classloader\\chapter3\\MyObject.class1";

        doEncrypt(source, target);
    }
}


