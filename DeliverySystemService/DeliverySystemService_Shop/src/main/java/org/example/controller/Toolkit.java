package org.example.controller;

import java.io.*;
import java.util.*;


/**
 * 说明:
 * 建立时间: 2009-12-22 14:08:52
 *
 * @author: lixingchen
 */
public class Toolkit {

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param path 图片路径
     * @return base64编码
     */
    public static String image2Base64(String path) {
        byte[] data = null;
        Base64.Encoder encoder = Base64.getEncoder();
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        // 返回Base64编码过的字节数组字符串
        return encoder.encodeToString(data);
    }
}
