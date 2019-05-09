package com.ypb.classloader.chapter4;

public class SImpleEncrypt {

    private static final String plian = "Hello Yangpengbing";
    private static final byte ENCRYPT_FACTOR = (byte) 0xff;

    public static void main(String[] args) {
        byte[] bytes = plian.getBytes();

        bytes = coding(bytes);
        System.out.println("new String(encrypt) = " + new String(bytes));

        bytes = coding(bytes);
        System.out.println("new String(decrypt) = " + new String(bytes));
    }

    private static byte[] coding(byte[] bytes) {
        byte[] coding = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            coding[i] = (byte) (bytes[i] ^ ENCRYPT_FACTOR);
        }

        return coding;
    }
}
