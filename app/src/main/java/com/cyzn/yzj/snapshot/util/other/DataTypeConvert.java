package com.cyzn.yzj.snapshot.util.other;

/**
 * @author YZJ
 * @description 数据类型转换util类
 * @date 2018/10/19
 */
public class DataTypeConvert {

    /**
     * byte数组转换为二进制字符串,每个字节以","隔开
     **/
    public static String convert2HexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 2) + ",");
        }
        return result.toString().substring(0, result.length() - 1);
    }

    /**
     * 二进制字符串转换为byte数组,每个字节以","隔开
     **/
    public static byte[] convert2HexToByte(String hex2Str) {
        String[] temp = hex2Str.split(",");
        byte[] b = new byte[temp.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = Long.valueOf(temp[i], 2).byteValue();
        }
        return b;
    }
}
