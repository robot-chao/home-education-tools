package cn.luckypapa.homeeducation.wechat.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Table(name = "t_wechat_response")
@Entity
@ToString
public class WechatResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String userMsg;
    private String responseType;
    private String content;
    private String title;
    private String description;
    private String picUrl;
    private String url;
    private Date createTime;
    private Date updateTime;

    public WechatResponseEntity() {}

    public WechatResponseEntity(String userMsg, String responseType, String content) {
        this.userMsg = userMsg;
        this.responseType = responseType;
        this.content = content;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public WechatResponseEntity(String userMsg, String responseType,
                                String title, String description, String picUrl, String url) {
        this.userMsg = userMsg;
        this.responseType = responseType;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
        this.createTime = new Date();
        this.updateTime = new Date();
    }
}
