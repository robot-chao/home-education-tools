package cn.luckypapa.homeeducation.wechat;

import org.springframework.stereotype.Component;

@Component
public class WechatImageRequestProcessor extends WechatRequestProcessor {

    @Override
    protected WechatRequestTypeEnum getSupportMsgType() {
        return WechatRequestTypeEnum.IMAGE;
    }

    @Override
    public WechatResponse process(WechatRequest wechatRequest) {
        return new WechatNonPayloadResponse();
    }
}
