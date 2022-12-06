package cn.luckypapa.homeeducation.tools.paperscan;

import com.baidu.aip.ocr.AipOcr;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("baiduOCRService")
public class BaiduOCRClient {
    public static final String APP_ID = "你的 App ID";
    public static final String API_KEY = "你的 Api Key";
    public static final String SECRET_KEY = "你的 Secret Key";

    private AipOcr ocrClient;

    @PostConstruct
    public void init() {
        // 初始化一个AipOcr
        this.ocrClient = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        this.ocrClient.setConnectionTimeoutInMillis(2000);
        this.ocrClient.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        // System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
    }

    public void scanPaper() {
        // 调用接口
        String path = "test.jpg";
        JSONObject res = this.ocrClient.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
    }
}
