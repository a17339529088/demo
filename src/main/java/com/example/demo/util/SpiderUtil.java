package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 爬虫工具类
 *
 * @author xiaoyang
 * @date 2022/7/24
 */
public class SpiderUtil {
    private final List<String> userAgent;

    public SpiderUtil() {
        ArrayList<String> users = new ArrayList<>();
        users.add("Mozilla/4.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        this.userAgent = users;
    }

    /**
     * 请求头的设备名
     *
     * @return UserAgent
     */
    public String getUserAgent() {
        return userAgent.get(0);
    }

    /**
     * 请求头的随机设备名
     *
     * @return UserAgent
     */
    public String getRandomUserAgent() {
        return userAgent.get(new Random().nextInt(userAgent.size()));
    }

    /**
     * 生成伪造ip
     *
     * @return 虚拟ip
     */
    public String getRandomIp() {
        int i1 = new Random().nextInt(9);
        int i2 = new Random().nextInt(9);
        int i3 = new Random().nextInt(9);
        return "183.192.229." + i1 + i2 + i3;
    }

    /**
     * 随机暂停时间
     * 0-1000
     *
     * @return 随机数
     */
    public int getRandomTime() {
        return new Random().nextInt(1000);
    }
}
