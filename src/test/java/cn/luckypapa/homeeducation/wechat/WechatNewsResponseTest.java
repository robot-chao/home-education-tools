package cn.luckypapa.homeeducation.wechat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class WechatNewsResponseTest {

    @Test
    void payload() {
        WechatNewsResponse wechatNewsResponse = new WechatNewsResponse("toUserName", "fromUserName");
        wechatNewsResponse.addArticleItem("Welcome!", "Hello!", "pic", "url");
        String xml = wechatNewsResponse.payload();
        log.info(xml);

        assertTrue(xml.contains("<ToUserName><![CDATA[toUserName]]></ToUserName>"));
        assertTrue(xml.contains("<FromUserName><![CDATA[fromUserName]]></FromUserName>"));
        assertTrue(xml.contains("<CreateTime>"));
        assertTrue(xml.contains("</CreateTime>"));
        assertTrue(xml.contains("<MsgType><![CDATA[news]]></MsgType>"));
        assertTrue(xml.contains("<ArticleCount>1</ArticleCount>"));
        assertTrue(xml.contains("<Articles><Item><Title><![CDATA[Welcome!]]></Title><Description><![CDATA[Hello!]]></Description><PicUrl><![CDATA[pic]]></PicUrl><Url><![CDATA[url]]></Url></Item></Articles>"));
    }
}