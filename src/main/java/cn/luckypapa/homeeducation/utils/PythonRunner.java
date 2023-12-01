package cn.luckypapa.homeeducation.utils;

import java.io.IOException;

public class PythonRunner {

    public Object exec(String pythonFile) {
        String[] cmd = new String[]{"python3", pythonFile};
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "success";
    }
}
