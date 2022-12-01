package cn.luckypapa.homeeducation.tools.arithmetic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ArithmeticPaper {
    
    private String uuid;
    private Date createTime;
    private int opCount;
    private int itemCount;
    private List<String> items;

    public ArithmeticPaper(int opCount, List<String> items) {
        this.opCount = opCount;
        this.items = new ArrayList<>(items);
        this.uuid = UUID.randomUUID().toString();
        this.createTime = new Date();
        this.itemCount = this.items.size();
    }
}
