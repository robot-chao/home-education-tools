package cn.luckypapa.homeeducation.wechat;

import cn.luckypapa.homeeducation.wechat.response.WechatTextResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class WechatTextResponseTest {

    @Test
    void payload() {
        WechatTextResponse wechatTextResponse = new WechatTextResponse(
                "toName", "fromUser", "Hello!");
        String xml = wechatTextResponse.payload();
        log.info(xml);

        assertTrue(xml.contains("<ToUserName><![CDATA[toName]]></ToUserName>"));
        assertTrue(xml.contains("<FromUserName><![CDATA[fromUser]]></FromUserName>"));
        assertTrue(xml.contains("<CreateTime>"));
        assertTrue(xml.contains("</CreateTime>"));
        assertTrue(xml.contains("<MsgType><![CDATA[text]]></MsgType>"));
        assertTrue(xml.contains("<Content><![CDATA[Hello!]]></Content>"));
    }
}