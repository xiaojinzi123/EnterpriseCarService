package com.ehi.enterprise;

public class EHiConstant {

    private EHiConstant() {
        throw new UnsupportedOperationException("can't create EHiConstant object");
    }

    /**
     * fileProvider
     */
    public static final String EHI_FILE_PROVIDER_AUTHORITIES = "com.ehi.enterprise.fileProvider";

    /**
     * 客服的电话
     */
    public static final String CUSTOMER_SERVICE_PHONE = "4008886608";

    /**
     * 生成的代码调用ApiManager 的时候的前缀
     */
    public static final String API_CALL_PATH_PREFIX = "com.ehai.network.retrofit.ApiManager.INSTANCE";

    /**
     * 有些界面的功能可能不单一,比如进入选择门店的目的目前可能有两：
     * 1.想拿到用户选择的某一个门店的数据
     * 2.用户浏览所有的门店,然后进入门店详情中进行预约
     * 可能还有第三种情况以后,所以这种情况,我额外为所有的这些场景的界面定义一个
     * 统一的key,表示目标界面的功能
     */
    public static final String EXTRA_FUNCTION_TYPE = "functionType";


    //===============================有关Retrofit请求的一些常量 start============================

    // 表示请求需要加密body,接口描述中,null或者true是默认的值
    public static final String HEADER_ENCRYPT_BODY_KEY = "IsEncryptBody";
    public static final String HEADER_ENCRYPT_BODY = HEADER_ENCRYPT_BODY_KEY + ":true";
    public static final String HEADER_UNENCRYPT_BODY = HEADER_ENCRYPT_BODY_KEY + ":false";

    // 表示响应需要解密body,接口描述中,null或者true是默认的值
    public static final String HEADER_DECODE_BODY_KEY = "IsDecodeBody";
    public static final String HEADER_DECODE_BODY = HEADER_DECODE_BODY_KEY + ":true";
    public static final String HEADER_UNDECODE_BODY = HEADER_DECODE_BODY_KEY + ":false";

    // 用来标识一个请求的body就是一个字符串
    public static final String HEADER_REQUEST_STRING_BODY_KEY = "IsStringBody";
    public static final String HEADER_REQUEST_STRING_BODY = HEADER_REQUEST_STRING_BODY_KEY + ":true";

    // 因为这里标识了才会在拦截器中判断到
    // 标识使用新的请求的结构,不用body中拼接固定的结构数据,值为0或者null是默认的值
    // 另外这里取名字是1结尾的是表示,可能以后会出现更多的形式,不是true和false能解决的
    public static final String HEADER_REQUEST_STRUCTURE_KEY = "RequestStructure";

    // 请求body中不需要添加那些固定的参数
    public static final String HEADER_REQUEST_STRUCTURE_1_VALUE = "1";
    public static final String HEADER_REQUEST_STRUCTURE_1 = HEADER_REQUEST_STRUCTURE_KEY + ":" + HEADER_REQUEST_STRUCTURE_1_VALUE;

    // 请求是最原始的请求格式,不做任何的处理
    public static final String HEADER_REQUEST_STRUCTURE_2_VALUE = "2";
    public static final String HEADER_REQUEST_STRUCTURE_2 = HEADER_REQUEST_STRUCTURE_KEY + ":" + HEADER_REQUEST_STRUCTURE_2_VALUE;

    //===============================有关Retrofit请求的一些常量 end==============================


}