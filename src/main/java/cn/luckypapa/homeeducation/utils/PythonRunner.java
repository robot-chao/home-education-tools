package cn.luckypapa.homeeducation.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class PythonRunner {

    public String exec(String pythonFile, String[] args) {
        String[] cmd = new String[]{"python3", pythonFile};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(ArrayUtils.addAll(cmd, args));

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

            String line = null;
            StringBuilder inBuilder = new StringBuilder();
            StringBuilder errorBuilder = new StringBuilder();
            while ((line = in.readLine()) != null) {
                inBuilder.append(line);
            }

            while ((line = error.readLine()) != null) {
                errorBuilder.append(line);
            }
            in.close();
            process.waitFor();
            log.info("inputstream: {}", inBuilder.toString());
            log.info("errorstream: {}", errorBuilder.toString());
            return inBuilder.toString();
        } catch (IOException e) {
            log.info("exec python error", e);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.info("exec python error", e);
            throw new RuntimeException(e);
        }
    }
}
