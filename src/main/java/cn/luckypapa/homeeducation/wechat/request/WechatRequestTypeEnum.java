package cn.luckypapa.homeeducation.wechat.request;

public enum WechatRequestTypeEnum {
    TEXT("text"), IMAGE("image");

    private String msgType;

    WechatRequestTypeEnum(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }
}
