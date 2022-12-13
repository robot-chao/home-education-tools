package cn.luckypapa.homeeducation.wechat.processor;

import cn.luckypapa.homeeducation.wechat.request.WechatRequestTypeEnum;
import cn.luckypapa.homeeducation.wechat.request.WechatRequest;
import cn.luckypapa.homeeducation.wechat.response.WechatNonPayloadResponse;
import cn.luckypapa.homeeducation.wechat.response.WechatResponse;
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
