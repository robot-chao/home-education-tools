package cn.luckypapa.homeeducation.wechat;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatServer {

    public static final String PARAM_ECHO_STR = "echostr";
    public static final String PARAM_TIMESTAMP = "timestamp";
    public static final String PARAM_NONCE = "nonce";
    public static final String PARAM_SIGNATURE = "signature";

    @Value("${wechat.server.token}")
    private String wechatServerToken;

    @Autowired
    private IWechatCallbackService wechatCallbackService;

    @RequestMapping("/dispatcher")
    public Object dispatcher(HttpServletRequest request, @RequestBody(required = false) String msg) {
        if (!isFromWechatServer(request)) {
            log.info("error");
            return "error";
        }

        /**
         * 用于响应微信的服务器确认请求
         */
        String echoStr = request.getParameter(PARAM_ECHO_STR);
        if (StringUtils.isNotBlank(echoStr)) {
            return echoStr;
        }

        return wechatCallbackService.doCallback(msg);
    }

    private boolean isFromWechatServer(HttpServletRequest request) {
        String signature = request.getParameter(PARAM_SIGNATURE);
        if (StringUtils.isBlank(signature)) return false;

        String timestamp = StringUtils.trimToEmpty(request.getParameter(PARAM_TIMESTAMP));
        String nonce = StringUtils.trimToEmpty(request.getParameter(PARAM_NONCE));

        String[] params = new String[] {this.wechatServerToken, timestamp, nonce};
        Arrays.sort(params);
        log.info("params: {}, sign: {}", Arrays.toString(params), signature);
        String sha1 = DigestUtils.sha1Hex(StringUtils.join(params, ""));

        return StringUtils.equals(signature, sha1);
    }
}
