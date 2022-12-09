package cn.luckypapa.homeeducation.wechat;

import cn.luckypapa.homeeducation.wechat.dao.WechatResponseEntity;
import cn.luckypapa.homeeducation.wechat.dao.WechatResponseJpaRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class WechatTextRequestProcessor extends WechatRequestProcessor {

    @Resource
    private WechatResponseJpaRepository wechatResponseJpaRepository;

    @Override
    protected WechatRequestTypeEnum getSupportMsgType() {
        return WechatRequestTypeEnum.TEXT;
    }

    @Override
    public WechatResponse process(WechatRequest wechatRequest) {
        WechatTextRequest wechatTextRequest = (WechatTextRequest) wechatRequest;

        WechatResponseEntity wechatResponseEntity = getResponseByUserMsg(wechatTextRequest);

        if (null == wechatResponseEntity) {
            return getDefaultResponse(wechatRequest);
        }

        if ("news".equals(wechatResponseEntity.getResponseType())) {
            WechatNewsResponse wechatNewsResponse = new WechatNewsResponse(
                    wechatRequest.getFromUserName(), wechatRequest.getToUserName());
            wechatNewsResponse.addArticleItem(wechatResponseEntity.getTitle(),
                    wechatResponseEntity.getDescription(), wechatResponseEntity.getPicUrl(),
                    wechatResponseEntity.getUrl());

            return wechatNewsResponse;
        } else {
            return new WechatTextResponse(wechatRequest.getFromUserName(), wechatRequest.getToUserName(),
                    wechatResponseEntity.getContent());
        }
    }

    private WechatResponse getDefaultResponse(WechatRequest wechatRequest) {
        return new WechatTextResponse(wechatRequest.getFromUserName(), wechatRequest.getToUserName(),
                "Welcome!\n回复“四则运算”获取四则运算试卷。");
    }

    private WechatResponseEntity getResponseByUserMsg(WechatTextRequest wechatTextRequest) {
        WechatResponseEntity condition = new WechatResponseEntity();
        condition.setUserMsg(wechatTextRequest.getContent());
        return wechatResponseJpaRepository.getByUserMsg(wechatTextRequest.getContent());
    }
}
