package com.ypb.classloader.chapter3;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yangpengbing
 * @version 1.0.0
 * @className MyClassLoader
 * @description 自定义类加载器，加载磁盘上的class文件
 * @date 22:50 2019/5/7
 */
public class MyClassLoader extends ClassLoader {

    public static final String DEFAULT_DIR = "E:\\temp\\customclassloader\\classloader1\\";

    @Setter
    private String dir = DEFAULT_DIR;

    @Getter
    private String classLoaderName;

    public MyClassLoader() {
        super();
    }

    public MyClassLoader(String classLoaderName) {
        super();
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(String classLoaderName, ClassLoader loader) {
        super(loader);
        this.classLoaderName = classLoaderName;
    }

    /**
     * xx.xx.xx.AA
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        File classFile = new File(dir, classPath + ".class");

        if (!classFile.exists()) {
            throw new ClassNotFoundException("the claas " + name + " not found under " + dir);
        }

        byte[] bytes = loadClassBytes(classFile);
        if (bytes == null || bytes.length == 0) {
            throw new ClassNotFoundException("load the class " + name + " failed");
        }

        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile)
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**************************** 学习笔记(2019年5月7日) ******************************/
//    自定义类加载器，需要继承ClassLoader类，重写findClass方法即可

//    Spring的replace不使用正则替换所有的，而replaceAll是使用正则替换所有的，而replaceFirst是使用正则替换第一个
}
