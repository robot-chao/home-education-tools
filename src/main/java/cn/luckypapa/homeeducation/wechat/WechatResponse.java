package cn.luckypapa.homeeducation.wechat;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public abstract class WechatResponse {
    protected String toUserName;
    protected String fromUserName;
    protected long createTime;
    protected WechatResponseTypeEnum msgType;

    public WechatResponse(String toUserName, String fromUserName,
                          long createTime, WechatResponseTypeEnum msgType) {
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = createTime;
        this.msgType = msgType;
    }

    protected Document createXmlDoc() {
        Document document = DocumentHelper.createDocument();

        Element xml = document.addElement("xml");
        xml.addElement("ToUserName").addCDATA(toUserName);
        xml.addElement("FromUserName").addCDATA(fromUserName);
        xml.addElement("CreateTime").addText("" + createTime);
        xml.addElement("MsgType").addCDATA(msgType.getMsgType());

        return document;
    }

    public abstract String payload();
}
