package com.jd.generater.worker;

import com.jd.generater.domain.UrlFileConfig;
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

    private UrlFileConfig config;
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


    public GeneraterWorker(UrlFileConfig config, String ip, String encoding) {
        this.config = config;
        this.ip = ip;
        this.encoding = encoding;
    }

    @Override
    public void run() {
        String newContent = HttpUtil.getStringFromUrl(config.getUrl(), encoding, ip);
        if (!HttpUtil.ERROR.equals(newContent) && newContent != null) {
            if (config.getKey() != null && newContent.contains(config.getKey())) {
                if (!sameCache(config.getPath(), newContent)) {
                    try {
                        FileUtil.writeToFile(config.getPath(), newContent, encoding);
                        GeneraterManager.setCache(config.getPath(), newContent);
                        logger.info(new Date().toGMTString() + " create new file success,filePath is " + config.getPath());
                    } catch (IOException e) {
                        logger.error("writeToFile error,filePath is " + config.getPath() + ";encoding is " + encoding, e);
                    }
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

    public UrlFileConfig getConfig() {
        return config;
    }

    public void setConfig(UrlFileConfig config) {
        this.config = config;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
