package com.ehi.datasource.compiler;

/**
 * time   : 2018/10/11
 *
 * @author : xiaojinzi 30212
 */
public class RouterBean {

    private String host;
    private String path;
    private String desc;
    private boolean needLogin;

    public RouterBean() {
    }

    public RouterBean(String host, String path, String desc, boolean needLogin) {
        this.host = host;
        this.path = path;
        this.desc = desc;
        this.needLogin = needLogin;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    @Override
    public String toString() {
        return "RouterBean{" +
                "host='" + host + '\'' +
                ", path='" + path + '\'' +
                ", desc='" + desc + '\'' +
                ", needLogin=" + needLogin +
                '}';
    }

}
