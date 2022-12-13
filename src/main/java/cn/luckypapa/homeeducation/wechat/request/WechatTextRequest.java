package cn.luckypapa.homeeducation.wechat.request;

import lombok.Getter;
import org.dom4j.Document;

@Getter
public class WechatTextRequest extends WechatRequest {

    private String content;

    public WechatTextRequest(Document xmlDocument) {
        super(xmlDocument, WechatRequestTypeEnum.TEXT);
        this.content = xmlDocument.getRootElement().elementText("Content");
    }
}
