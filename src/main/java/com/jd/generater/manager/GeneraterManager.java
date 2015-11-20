package com.jd.generater.manager;

import com.jd.generater.util.FileUtil;
import com.jd.generater.worker.GeneraterWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by caozhifei on 2015/11/20.
 */
public class GeneraterManager {
    private final static Logger logger = LoggerFactory.getLogger(GeneraterManager.class);
    /**
     * 文件缓存map
     */
    private final static ConcurrentHashMap<String, String> fileCacheMap = new ConcurrentHashMap<String, String>();
    /**
     * 访问页面静态文件后台任务
     */
    private ScheduledExecutorService generaterTask;
    /**
     * worker线程池
     */
    private ExecutorService executorWorker;
    /**
     * URL 文件路径映射集合
     */
    private Map<String, String> urlFileMap;
    /**
     * 指定ip
     */
    private String ip;
    /**
     * 指定编码
     */
    private String encoding;
    /**
     * 延迟启动时间，单位秒
     */
    private Long delay;
    /**
     * 默认延迟启动时间
     */
    private final static Long defaultDelay = 10L;
    /**
     * 周期执行时间，单位秒
     */
    private Long period;
    /**
     * 默认周期执行时间
     */
    private final static Long defaultPeriod = 120L;

    public GeneraterManager(Map<String, String> urlFileMap, String ip, String encoding) {
        this(urlFileMap, ip, encoding, null, null);
    }

    public GeneraterManager(Map<String, String> urlFileMap, String ip, String encoding, Long delay) {
        this(urlFileMap, ip, encoding, delay, null);
    }

    public GeneraterManager(Map<String, String> urlFileMap, String ip, Long period, String encoding) {
        this(urlFileMap, ip, encoding, null, period);
    }

    public GeneraterManager(Map<String, String> urlFileMap, String ip, String encoding, Long delay, Long period) {
        this.urlFileMap = urlFileMap;
        this.ip = ip;
        this.encoding = encoding;
        this.delay = delay == null ? defaultDelay : delay;
        this.period = period == null ? defaultPeriod : period;
        executorWorker = Executors.newFixedThreadPool(urlFileMap.size());
        initLocalFileCache();
        initTask();
    }

    /**
     * 初始化计划任务
     */
    private void initTask() {
        generaterTask = Executors.newSingleThreadScheduledExecutor();
        generaterTask.scheduleAtFixedRate(new Runnable() {
            public void run() {
                doWork();
            }
        }, delay, period, TimeUnit.SECONDS);
    }

    /**
     * 进行url的静态化具体操作
     */
    private void doWork() {
        if (urlFileMap == null || urlFileMap.isEmpty()) {
            return;
        }
        for (String key : urlFileMap.keySet()) {
            GeneraterWorker worker = new GeneraterWorker(key, urlFileMap.get(key), ip, encoding);
            executorWorker.execute(worker);
        }
    }

    /**
     * 初始化文件缓存
     */
    private void initLocalFileCache() {
        if (urlFileMap == null || urlFileMap.isEmpty()) {
            return;
        }
        for (String key : urlFileMap.keySet()) {
            String filePath = urlFileMap.get(key);
            try {
                String content = FileUtil.readFromFile(filePath, encoding);
                if (content != null) {
                    setCache(filePath, content);
                    logger.info("initLocalFileCache success,filePath is " + filePath);
                }
            } catch (IOException e) {
                logger.error("initFileCache error,the filePath is " + filePath + ";encoding is " + encoding, e);
            }
        }
    }

    /**
     * 销毁所有后台线程
     */
    public void destroy() {
        generaterTask.shutdown();
        executorWorker.shutdown();
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void setCache(String key, String value) {
        fileCacheMap.put(key, value);
    }

    /**
     * 取出缓存
     *
     * @param key
     */
    public static String getCache(String key) {
        if (fileCacheMap.containsKey(key)) {
            return fileCacheMap.get(key);
        }
        return null;
    }
    public static void main(String[] args){
        Map<String,String> map = new HashMap<String, String>();
        map.put("http://try.jd.com","D:/export/Data/try.jd.local/try/index.html");
        String ip = "172.17.26.36";
        String encoding = "UTF-8";
        GeneraterManager manager = new GeneraterManager(map,ip,encoding);
        System.out.println("success");
    }
}
