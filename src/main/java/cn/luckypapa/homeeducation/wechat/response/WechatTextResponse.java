package cn.luckypapa.homeeducation.wechat;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXWriter;

import java.util.Date;

/**
 * 回复微信文本消息
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>12345678</CreateTime>
 *   <MsgType><![CDATA[text]]></MsgType>
 *   <Content><![CDATA[你好]]></Content>
 * </xml>
 */
public class WechatTextResponse extends WechatResponse {

    private String content;

    public WechatTextResponse(String toUserName, String fromUserName, String content) {
        super(toUserName, fromUserName, new Date().getTime(), WechatResponseTypeEnum.TEXT);
        this.content = content;
    }

    @Override
    public String payload() {
        Document xmlDoc = super.createXmlDoc();
        Element xml = xmlDoc.getRootElement();
        xml.addElement("Content").addCDATA(content);

        return xmlDoc.asXML();
    }
}
