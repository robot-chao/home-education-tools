package cn.luckypapa.homeeducation.wechat.response;

/**
 * 无内容响应
 */
public class WechatNonPayloadResponse extends WechatResponse {

    public WechatNonPayloadResponse() {
        super("", "", 0l, WechatResponseTypeEnum.DEFAULT);
    }

    public String payload() {
        return "success";
    }
}
