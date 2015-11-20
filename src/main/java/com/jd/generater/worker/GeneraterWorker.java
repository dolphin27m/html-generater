package com.jd.generater.worker;

import com.jd.generater.manager.GeneraterManager;
import com.jd.generater.util.FileUtil;
import com.jd.generater.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Created by caozhifei on 2015/11/20.
 */
public class GeneraterWorker implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(GeneraterWorker.class);

    /**
     * 访问url
     */
    private String url;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 指定访问ip
     */
    private String ip;
    /**
     * 编码
     */
    private String encoding;
    /**
     * 默认指定编码
     */
    private final static String defaultEncoding = "UTF-8";

    public GeneraterWorker(String url, String filePath, String ip) {
        this(url, filePath, ip, defaultEncoding);
    }

    public GeneraterWorker(String url, String filePath, String ip, String encoding) {
        this.url = url;
        this.filePath = filePath;
        this.ip = ip;
        this.encoding = encoding;
    }

    @Override
    public void run() {
        String newContent = HttpUtil.getStringFromUrl(url, encoding, ip);
        if (!HttpUtil.ERROR.equals(newContent)) {
            if (!sameCache(filePath, newContent)) {
                try {
                    FileUtil.writeToFile(filePath, newContent, encoding);
                    GeneraterManager.setCache(filePath,newContent);
                    logger.info(new Date().toGMTString()+" create new file success,filePath is "+filePath);
                } catch (IOException e) {
                    logger.error("writeToFile error,filePath is " + filePath + ";encoding is " + encoding, e);
                }
            }
        }
    }

    /**
     * 检查请求结果与缓存的文件内容是否相同
     *
     * @param key
     * @param newContent
     * @return
     */
    private boolean sameCache(String key, String newContent) {
        String cache = GeneraterManager.getCache(key);
        if (cache == null) {
            return false;
        }
        if (newContent == null || newContent == "") {
            return true;
        }
        if (newContent.equals(cache)) {
            return true;
        }
        return false;

    }

}
