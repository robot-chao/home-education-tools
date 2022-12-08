package cn.luckypapa.homeeducation.wechat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WechatRequestTest {

    @Test
    void parseRequest() {
        WechatRequest wechatTextRequest = WechatRequest.parseRequest(
                "<xml>\n" +
                        "    <ToUserName><![CDATA[gh_60c6a09a45a7]]></ToUserName>\n" +
                        "    <FromUserName><![CDATA[oscLmsgdAbYsWy8Cwavt_8IPvbX8]]></FromUserName>\n" +
                        "    <CreateTime>1670401016</CreateTime>\n" +
                        "    <MsgType><![CDATA[text]]></MsgType>\n" +
                        "    <Content><![CDATA[是的]]></Content>\n" +
                        "    <MsgId>23914390881558974</MsgId>\n" +
                        "</xml>");

        assertTrue(wechatTextRequest instanceof WechatTextRequest);
        assertEquals("是的", ((WechatTextRequest) wechatTextRequest).getContent());

        WechatRequest wechatImageRequest = WechatRequest.parseRequest(
                "<xml>\n" +
                        "    <ToUserName><![CDATA[gh_60c6a09a45a7]]></ToUserName>\n" +
                        "    <FromUserName><![CDATA[oscLmsgdAbYsWy8Cwavt_8IPvbX8]]></FromUserName>\n" +
                        "    <CreateTime>1670401072</CreateTime>\n" +
                        "    <MsgType><![CDATA[image]]></MsgType>\n" +
                        "    <PicUrl><![CDATA[http://mmbiz.qpic.cn/mmbiz_jpg/6WPibnq2xicRh1ic6L94BRdmrYicUhjclk2Jmqnb93jLZXichBkjOywebpJWWYmESfLqbGekmkic5u4kKsiacrmTPZIicA/0]]></PicUrl>\n" +
                        "    <MsgId>23914390994442490</MsgId>\n" +
                        "    <MediaId><![CDATA[6ZwpYe7s_4fcZEUd9hNjFPjTVgnIr2Qeie1ZkfdihYfqZ-sUiSjkLiVW56KH_3RO]]></MediaId>\n" +
                        "</xml>");

        assertTrue(wechatImageRequest instanceof WechatImageRequest);
        assertEquals("http://mmbiz.qpic.cn/mmbiz_jpg/6WPibnq2xicRh1ic6L94BRdmrYicUhjclk2Jmqnb93jLZXichBkjOywebpJWWYmESfLqbGekmkic5u4kKsiacrmTPZIicA/0",
                ((WechatImageRequest) wechatImageRequest).getPicUrl());

    }
}