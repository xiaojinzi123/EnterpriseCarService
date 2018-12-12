package com.ehi.enterprise;

/**
 * 组件化的业务组件配置中心
 * time   : 2018/11/27
 *
 * @author : xiaojinzi 30212
 */
public class ModuleConfig {

    public static class App {

        public static final String NAME = "app";

    }

    public static class User {

        // 模块名字
        public static final String NAME = "user";

        // 登录界面
        public static final String USER_LOGIN = "login";

    }

}
