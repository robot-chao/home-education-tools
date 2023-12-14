package cn.luckypapa.homeeducation.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;

@Slf4j
public class PythonRunner {

    public Object exec(String pythonFile, String[] args) {
        String[] cmd = new String[]{"python3", pythonFile};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(ArrayUtils.addAll(cmd, args));
            process.waitFor();
        } catch (IOException e) {
            log.info("exec python error", e);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.info("exec python error", e);
            throw new RuntimeException(e);
        }

        return "success";
    }
}
