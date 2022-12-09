package cn.luckypapa.homeeducation.wechat.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class WechatResponseJpaRepositoryTest {

    @Autowired
    private WechatResponseJpaRepository wechatResponseJpaRepository;

    @Test
    void getByUserMsg() {
        WechatResponseEntity entity = new WechatResponseEntity("四则运算", "news",
                "点击获取四则运算试卷", "点击获取四则运算试卷", "picUrl", "url");
        wechatResponseJpaRepository.save(entity);
        WechatResponseEntity wechatResponseEntity = wechatResponseJpaRepository.getByUserMsg("四则运算");
        log.info("find: {}", wechatResponseEntity.toString());
        assertNotNull(wechatResponseEntity);
    }
}