package com.mrrightli.week01;

import java.io.*;
import java.lang.reflect.Method;


public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> helloClazz= new MyClassLoader().findClass("Hello");
        Object object = helloClazz.newInstance();

        Method helloMethod = helloClazz.getMethod("hello");
        helloMethod.invoke(object);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        try {
            bytes = getContent("/Users/mrrightli/Documents/workplace/java/java-homework/src/main/java/com/mrrightli/week01/Hello.xlass");
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(name, bytes, 0, bytes.length);
    }

    public byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }
}
