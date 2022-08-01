package com.example.demo.controller;


import com.example.demo.util.Md5Utils;
import com.example.demo.util.SpiderUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author xiaoyang
 * @date 2022/7/29
 */
@Component
@Order(1)
@Slf4j
public class GongShi implements ApplicationRunner {
    @Value("${user.account}")
    private String USER_NAME;
    @Value("${user.password}")
    private String PASSWORD;
    @Autowired
    private SpiderUtil spiderUtil;

    /**
     * 登录系统
     * 拿到登录后的cookies,后续请求全部携带
     *
     * @return cookies
     */
    Map<String, String> userLogin() throws IOException, InterruptedException {
        // 登录表单
        HashMap<String, String> userData = new HashMap<>();
        userData.put("NAME", USER_NAME);
        userData.put("PASSWORD", Objects.requireNonNull(Md5Utils.md5(USER_NAME + PASSWORD)).toLowerCase(Locale.ROOT));
        userData.put("userType", "1");
        userData.put("token", "");
        // cookie
        Map<String, String> cookies = new HashMap<>();
        // 两次登录
        for (int i = 0; i < 2; i++) {
            String url = "http://gs.lxgs:26800/WorkHoursSystem/loginAction.do?action=login";
            Connection.Response execute = Jsoup.connect(url)
                    .userAgent(spiderUtil.getUserAgent())
                    .data(userData)
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .execute();
            if (cookies.size() == 0) {
                cookies = execute.cookies();
            }

            Document document = execute.parse();
            userData.put("token", document.select("#token").val());
            Thread.sleep(spiderUtil.getRandomTime());
        }
        System.out.println(cookies);
        return cookies;
    }



    /**
     * 添加工时
     *
     * @param sign      加密盐 (可能是token值,待验证)
     * @param member_id 加密盐2 用户id
     * @param time      需要填报的时间 yyyy-MM-dd格式
     * @param cookies   当前用户cookie,登录信息
     * @return 是否添加成功
     * @throws InterruptedException 异常
     * @throws IOException          异常
     */
    boolean addWorkHours(String sign, String member_id, String time, Map<String, String> cookies) throws InterruptedException, IOException {
        Thread.sleep(spiderUtil.getRandomTime());
        // 加密校验数据
        String md5Sign = Md5Utils.md5(sign + member_id + time);
        // 封装 添加工时
        HashMap<String, String> data = new HashMap<>();
        data.put("WORK_DATE", time);
        data.put("sign", md5Sign);
        // 常量
        data.put("PROJECT_ID", "6684");
        data.put("CLASS_ID", "199");
        data.put("SECONDE_CLASS_ID", "892");
        data.put("WORKHOURS", "8.5");
        data.put("DESCRIBE", "一键登录开发");
        data.put("TRIP", "0");

        String addUrl = "http://gs.lxgs:26800/WorkHoursSystem/workHoursAction.do?action=addWorkHours";
        Document post = Jsoup.connect(addUrl)
                .userAgent(spiderUtil.getUserAgent())
                .cookies(cookies)
                .data(data)
                .post();

        log.info("添加工时状态: {}, 时间: {}", post.body().text(), time);

        return post.body().text().equals("true");
    }

    @Override
        public void run(ApplicationArguments args) throws Exception {
        System.out.println(1111);
/*
            // 登录系统 获取cookie
            Map<String, String> cookies = userLogin();
            log.info("cookies: {}", cookies);
            // 访问主页
            String url = "http://gs.lxgs:26800/WorkHoursSystem/workHoursAction.do?action=getMemb_HoursList";
            Connection.Response execute = Jsoup.connect(url)
                    .userAgent(spiderUtil.getUserAgent())
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .execute();

            Document document = execute.parse();
            // 数据加密盐
            String sign = document.select("#sign").attr("value");
            String member_id = document.select("#member_id").attr("value");
            log.info("sign: {}, member_id: {}", sign, member_id);
            this.addWorkHours(sign, member_id, DateUtil.toStr(new Date(), "yyyy-MM-dd"), cookies);
*/
        System.exit(0);
    }
}
