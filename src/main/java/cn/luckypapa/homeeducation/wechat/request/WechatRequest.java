package cn.luckypapa.homeeducation.wechat;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Getter
@Slf4j
public abstract class WechatRequest {

    private String toUserName;
    private String fromUserName;
    private long createTime;
    private WechatRequestTypeEnum msgType;
    private String msgId;

    public WechatRequest(Document xmlDocument, WechatRequestTypeEnum msgType) {
        toUserName = xmlDocument.getRootElement().element("ToUserName").getText();
        fromUserName = xmlDocument.getRootElement().element("FromUserName").getText();
        createTime = Long.parseLong(xmlDocument.getRootElement().element("CreateTime").getText());
        msgId = xmlDocument.getRootElement().element("MsgId").getText();
        this.msgType = msgType;
    }

    public static WechatRequest parseRequest(String msg) {
        Document document = createDocument(msg);
        if (null == document) throw new RuntimeException("解析微信请求消息体失败");

        String msgType = document.getRootElement().element("MsgType").getText();

        if (StringUtils.equals(msgType, WechatRequestTypeEnum.TEXT.getMsgType())) {
            return new WechatTextRequest(document);
        } else if (StringUtils.equals(msgType, WechatRequestTypeEnum.IMAGE.getMsgType())) {
            return new WechatImageRequest(document);
        }

        return null;
    }

    private static Document createDocument(String msg) {
        SAXReader saxReader = new SAXReader();
        try {
            return saxReader.read(new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("SAXReader parse msg error!");
        }

        return null;
    }
}
