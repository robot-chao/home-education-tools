package cn.luckypapa.homeeducation.wechat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WechatTextRequestProcessor extends WechatRequestProcessor {

    public static final String PATH_ARITHMETIC = "/arithmetic/generate?opCount=3&itemCount=20";

    @Value("${domain.education}")
    private String educationDomain;

    @Override
    protected WechatRequestTypeEnum getSupportMsgType() {
        return WechatRequestTypeEnum.TEXT;
    }

    @Override
    public WechatResponse process(WechatRequest wechatRequest) {
        WechatTextRequest wechatTextRequest = (WechatTextRequest) wechatRequest;
        if ("四则运算".equals(wechatTextRequest.getContent())) {
            WechatNewsResponse wechatNewsResponse = new WechatNewsResponse(
                    wechatRequest.getFromUserName(), wechatRequest.getToUserName());
            wechatNewsResponse.addArticleItem("点击获取四则运算试卷", "点击获取四则运算试卷", "",
                    educationDomain + PATH_ARITHMETIC);

            return wechatNewsResponse;
        } else {
            return new WechatTextResponse(wechatRequest.getFromUserName(), wechatRequest.getToUserName(), "Welcome!");
        }
    }
}
