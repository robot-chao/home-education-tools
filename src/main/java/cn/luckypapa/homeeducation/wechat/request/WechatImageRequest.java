package cn.luckypapa.homeeducation.wechat;

import lombok.Getter;
import org.dom4j.Document;

@Getter
public class WechatImageRequest extends WechatRequest {
    private String picUrl;
    private String mediaId;

    public WechatImageRequest(Document xmlDocument) {
        super(xmlDocument, WechatRequestTypeEnum.IMAGE);
        this.picUrl = xmlDocument.getRootElement().elementText("PicUrl");
        this.mediaId = xmlDocument.getRootElement().elementText("MediaId");
    }
}
