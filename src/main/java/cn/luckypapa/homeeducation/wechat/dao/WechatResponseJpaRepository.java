package cn.luckypapa.homeeducation.wechat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WechatResponseJpaRepository extends JpaRepository<WechatResponseEntity, Integer> {

    WechatResponseEntity getByUserMsg(String userMsg);
}
