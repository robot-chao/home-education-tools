package cn.luckypapa.homeeducation.wechat.response;

public enum WechatResponseTypeEnum {

    TEXT("text"), NEWS("news"), DEFAULT("default");

    private String msgType;

    WechatResponseTypeEnum(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }
}
