package com.microsys.videodemo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 用于获取手机的文件夹及文件的工具类，如果权限允许，可以获取手机上任意路径的文件列表
 * GetFilesUtils使用的是懒汉式单例模式，线程安全
 *
 * @since 2016/1/19.
 */
public class GetFileUtils {

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     *
     * @param filePath 读取的文件路径
     */
    public static String readTxtFile(String filePath) {
        try {
            String encoding = "GBK";
            StringBuffer sb = new StringBuffer();
            File file = new File(filePath);
            //判断文件是否存在
            if (file.isFile() && file.exists()) {
                //考虑到编码格式
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    sb.append(lineTxt);
                }

                read.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
