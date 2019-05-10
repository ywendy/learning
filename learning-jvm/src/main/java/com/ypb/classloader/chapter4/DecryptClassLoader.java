package com.ypb.classloader.chapter4;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className DecryptClassLoader
 * @description 解密classloader
 * @date 23:07 2019/5/9
 */
@Slf4j
public class DecryptClassLoader extends ClassLoader {

    private static final String DIR = "E:\\temp\\customclassloader\\classloader4\\";

    @Setter
    private String dir;

    public DecryptClassLoader() {
        super();
    }

    public DecryptClassLoader(ClassLoader loader) {
        super(loader);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        File classFile = new File(DIR, classPath + ".class");

        if (!classFile.exists()) {
            throw new ClassNotFoundException("the class " + name + " not found under directory [" + DIR + "]");
        }

        byte[] classBytes = loadClassBytes(classFile);
        if (Objects.isNull(classBytes) || classBytes.length == 0) {
            throw new ClassNotFoundException("load the class " + name + " failed.");
        }

        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile)) {

            int data;
            while ((data = fis.read()) != -1) {
                baos.write(data ^ EncryptUtils.ENCRYPT_FACTOR);
            }

            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        return null;
    }
}
