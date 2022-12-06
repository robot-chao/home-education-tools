package cn.luckypapa.homeeducation.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WechatCallbackServiceImpl implements IWechatCallbackService{

    @Override
    public String doCallback(String msg) {
        log.info("接收到微信消息：{}", msg);
        return "success";
    }
}
