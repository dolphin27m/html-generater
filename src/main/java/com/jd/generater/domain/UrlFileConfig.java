package com.jd.generater.domain;

/**
 * Created by caozhifei on 2015/11/25.
 */
public class UrlFileConfig {
    private String url;
    private String path;
    private String key;

    public UrlFileConfig(String url, String path, String key) {
        this.url = url;
        this.path = path;
        this.key = key;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
