package com.ypb.classloader.chapter5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleClassLoader extends ClassLoader {

    private static final String DEFAULT_DIR = "E:\\temp\\customclassloader\\revert";
    @Getter
    private String classLoaderName;
    @Setter
    private String dir = DEFAULT_DIR;

    public SimpleClassLoader() {
        super();
    }

    public SimpleClassLoader(String classLoaderName) {
        super();
        this.classLoaderName = classLoaderName;
    }

    public SimpleClassLoader(String classLoaderName, ClassLoader loader) {
        super(loader);
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        File classFile = new File(dir, classPath + ".class");

        if (!classFile.exists()) {
            throw new ClassNotFoundException("the class " + name + " not found under " + dir);
        }

        byte[] bytes = loadClassBytes(classFile);

        return defineClass(name, bytes, 0, bytes.length);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> aClass = null;

        if (name.startsWith("java.lang.Object")) {
            aClass = getSystemClassLoader().loadClass(name);
            if (Objects.nonNull(aClass)) {
                if (resolve) {
                    resolveClass(aClass);
                }
            }

            return aClass;
        }

        try {
            aClass = findClass(name);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        if (Objects.isNull(aClass) && getParent() != null) {
            getParent().loadClass(name);
        }

        return aClass;
    }

    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile);
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        }

        return null;
    }
}
