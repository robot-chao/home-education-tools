package cn.luckypapa.homeeducation.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class PythonRunner {

    public Object exec(String pythonFile) {
        String[] cmd = new String[]{"python3", pythonFile};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
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
