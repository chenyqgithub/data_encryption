package com.data.encryption.util;

/**
 * Created by admin on 2018/4/2.
 */
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesUtil {

    private final static String DES = "DES";

    public static void main(String[] args) throws Exception {
        String data = "{\"jsonObject\":{},\"language\":\"zh\",\"os\":\"IOS\",\"userId\":\"3\",\"variable\":{\"countNum\":0,\"pageNo\":1,\"pageSize\":10},\"version\":\"1.1\"}";
        String key = "wow!@#$%";
        System.err.println(encrypt(data, key));
        System.err.println(decrypt("ZoeizLWXQs0u8n53wY1KkABvLVqp4GEd7vUjpXR2T4nqmggpLGHUrsEigRcCjVfUCHaVWYFle9yfRZcJRIsVJF28CW7FoJ88VkDn4B9yrerWugNVeGS+MgzQ/Xvqudg7EuSgOU9TpcqUZqaRHTlmDKnRAN39AI/+fbnDk6dhIEHxpRf7M2tFy3dAHNHBGXxp6/PYED9xXHembhS4v1CBAu9+aoZ9SBP6vrqGCRyVEdP+jxSzr3yk/vsqxIIJyhFrLgqXK/M+d5LAvtP0GPeAlR10PCt7tzVAnlB164ajR9Cv5vIxlhYWIjCKDhcPBeoSpb/636DW9RdKe5i10Tog7lG/7e7yFiYk2rOo4qp874CUfb0w+F5G/9amZfdZmX+KzRLj+/r9AugFtBmB21RFedt5PLaq8SHTDsOQ9hznROVcXI8os7SwgFti9JHQ5KQ1yzzp9t7cMpkLLJFv3rIQ25C72NiUePCfva2lyCLRobOO1tmV91fpwjIk8F6IyOL4jxKreNPq/IzbeTy2qvEh08E8rsPSw9grhvX4UmrfS3tu18a7m6oJ7ajKCEYFtHVoMfItIYQzn/i4vNFLeqiIducE3yxPPc4HU3GzO/kGibAHiSGdVKYACTdr6d2Gj6Ea5kioSqWPaxR5sC0m9ldy5mYAtC+Vo+dNN/eQSNch29K/KQZmFEHWTB65I2W+Jv2ML7XUf1DXxSS/h9/6F7RFqrIrqX69skL6IbF/YdqlMJK+uoYJHJUR0zoOy1wGkMw3+yrEggnKEWsuCpcr8z53ksC+0/QY94CV4HkRjK62ewmeUHXrhqNH0M3PkhIRA7gS403yprCZ3Eulv/rfoNb1F+rZyfQm7XYbUb/t7vIWJiTas6jiqnzvgJR9vTD4Xkb/nDj1etswqi3NEuP7+v0C6AW0GYHbVEV523k8tqrxIdNPWA8VaNeWY1DEZd6SLergW2L0kdDkpDWdjr0xucg0tQsskW/eshDbkx5IU8eDk0O9raXIItGhs47W2ZX3V+nCDmFli4VhphmPEqt40+r8jNt5PLaq8SHTwTyuw9LD2CsyOf5rWlHedG7XxrubqgntTQWcwmOdcA+mB2whdOTELbi80Ut6qIh25B25hLnwY3tTcbM7+QaJsAeJIZ1UpgAJe1ICL0aJRAvmSKhKpY9rFHmwLSb2V3LmZgC0L5Wj500KC7SVn/n7Lr8pBmYUQdZMEkuMjl1ISVijIUmt5Ig6Ob+H3/oXtEWq0MEzmmSiqvYhsX9h2qUwkr66hgkclRHTV4N/a+ekr+v7KsSCCcoRay4KlyvzPneSwL7T9Bj3gJVv8A+6gYHQxZ5QdeuGo0fQm9Q9sRSY84zVFc2WbWN29KW/+t+g1vUXJD+vgjSu6ApRv+3u8hYmJNqzqOKqfO+AlH29MPheRv/Axrp8xA2enM0S4/v6/QLoBbQZgdtURXnbeTy2qvEh0yV+d+zopFTT8Vx9KutFI0lbYvSR0OSkNTPZElmo+0FpCyyRb96yENvbJaj6QDrDyg==", key));
    }

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
