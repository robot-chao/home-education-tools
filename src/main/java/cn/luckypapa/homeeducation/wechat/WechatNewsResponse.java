package cn.luckypapa.homeeducation.wechat;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>12345678</CreateTime>
 *   <MsgType><![CDATA[news]]></MsgType>
 *   <ArticleCount>1</ArticleCount>
 *   <Articles>
 *     <item>
 *       <Title><![CDATA[title1]]></Title>
 *       <Description><![CDATA[description1]]></Description>
 *       <PicUrl><![CDATA[picurl]]></PicUrl>
 *       <Url><![CDATA[url]]></Url>
 *     </item>
 *   </Articles>
 * </xml>
 */
@Slf4j
public class WechatNewsResponse extends WechatResponse {

    private List<ArticleItem> articles = new ArrayList<>();

    public WechatNewsResponse(String toUserName, String fromUserName) {
        super(toUserName, fromUserName, new Date().getTime(), WechatResponseTypeEnum.NEWS);
    }

    public void addArticleItem(String title, String description, String picUrl, String url) {
        articles.add(new ArticleItem(title, description, picUrl, url));
    }

    @Override
    public String payload() {
        Document xmlDoc = super.createXmlDoc();
        Element xml = xmlDoc.getRootElement();
        xml.addElement("ArticleCount").addText("" + this.articles.size());
        Element articlesElement = xml.addElement("Articles");
        for (ArticleItem articleItem : this.articles) {
            articleItem.add2Element(articlesElement);
        }

        String payload = xmlDoc.asXML();

        log.info("payload: {}", payload);

        return payload;
    }

    class ArticleItem {
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public ArticleItem(String title, String description, String picUrl, String url) {
            this.title = title;
            this.description = description;
            this.picUrl = picUrl;
            this.url = url;
        }

        public void add2Element(Element articlesElement) {
            Element itemElement = articlesElement.addElement("Item");
            itemElement.addElement("Title").addCDATA(title);
            itemElement.addElement("Description").addCDATA(description);
            itemElement.addElement("PicUrl").addCDATA(picUrl);
            itemElement.addElement("Url").addCDATA(url);
        }
    }
}
