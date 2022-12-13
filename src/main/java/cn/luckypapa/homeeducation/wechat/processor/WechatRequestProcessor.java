package cn.luckypapa.homeeducation.wechat.processor;

import cn.luckypapa.homeeducation.wechat.request.WechatRequestTypeEnum;
import cn.luckypapa.homeeducation.wechat.request.WechatRequest;
import cn.luckypapa.homeeducation.wechat.response.WechatResponse;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

public abstract class WechatRequestProcessor {
    private static final Map<WechatRequestTypeEnum, WechatRequestProcessor> PROCESSOR_MAP = new HashMap<>();

    public static WechatRequestProcessor getProcessor(WechatRequestTypeEnum msgType) {
        return PROCESSOR_MAP.get(msgType);
    }

    @PostConstruct
    public void register() {
        PROCESSOR_MAP.put(getSupportMsgType(), this);
    }

    protected abstract WechatRequestTypeEnum getSupportMsgType();

    public abstract WechatResponse process(WechatRequest wechatRequest);
}
