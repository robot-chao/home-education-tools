package cn.luckypapa.homeeducation.wechat;

import cn.luckypapa.homeeducation.wechat.processor.WechatRequestProcessor;
import cn.luckypapa.homeeducation.wechat.request.WechatRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WechatCallbackServiceImpl implements IWechatCallbackService{

    @Override
    public String doCallback(String msg) {
        log.info("接收到微信消息：{}", msg);
        WechatRequest wechatRequest = WechatRequest.parseRequest(msg);
        return process(wechatRequest);
    }

    private String process(WechatRequest wechatRequest) {
        return getProcessor(wechatRequest).process(wechatRequest).payload();
    }

    private WechatRequestProcessor getProcessor(WechatRequest wechatRequest) {
        return WechatRequestProcessor.getProcessor(wechatRequest.getMsgType());
    }
}
