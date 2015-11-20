package com.jd.generater.util;


import java.io.*;
import java.nio.CharBuffer;

/**
 * Created by caozhifei on 2015/11/19.
 */
public class FileUtil {
    /**
     * 将数据写入文件，覆盖文件原有内容
     *
     * @param filePath 文件路径
     * @param content  写入文件内容
     * @param encoding 指定编码
     * @throws IOException
     */
    public static void writeToFile(String filePath, String content, String encoding) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), encoding);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();

    }

    /**
     * 从文件读取数据
     *
     * @param filePath 文件路径
     * @param encoding 指定编码
     * @return
     * @throws IOException
     */
    public static String readFromFile(String filePath, String encoding) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            return null;
        }
        StringBuilder sb = new StringBuilder(10240);
        InputStreamReader read = new InputStreamReader(
                new FileInputStream(filePath), encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        int len = 0;
        while (len != -1) {
            char[] chars = new char[1];
            len = bufferedReader.read(chars);
            if(len != -1) {
                sb.append(chars);
            }
        }
        read.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
         String filePath = "D:/export/Data/try.jd.local/try/index.html";
       //writeToFile(filePath,"111111","utf-8");
        System.out.println("content="+readFromFile(filePath,"utf-8"));

    }
}
