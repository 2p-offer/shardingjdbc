package com.erp.shardingjdbcdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.erp.shardingjdbcdemo.dao.NameRepository;
import com.erp.shardingjdbcdemo.domain.ShardingName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 2p
 * @date 2021/8/19 14:25
 * @desc TestController
 */
@Controller
public class TestController {

    @Autowired
    NameRepository nameRepository;

    @GetMapping("/insert")
    @ResponseBody
    public String doInsert(@RequestParam(value = "start") Integer start, @RequestParam(value = "end") Integer end) {
        List<ShardingName> container = new ArrayList<>();
        for (int i = start; i < end; i++) {
            ShardingName name = new ShardingName();
            name.setUId(i);
            name.setName("name:" + i);
            name.setAge(i + 20);
            name.setCreateTime(new Date());
            container.add(name);
        }
        List<ShardingName> names = nameRepository.saveAll(container);
        return JSONObject.toJSONString(names);
    }

    @GetMapping("/select")
    @ResponseBody
    public String doSelect(@RequestParam(value = "start") Integer start, @RequestParam(value = "end") Integer end) {
        List<ShardingName> container = new ArrayList<>();
        for (int i = start; i < end; i++) {
            ShardingName name = new ShardingName();
            name.setUId(i);
            name.setName("name:" + i);
            name.setAge(i + 20);
            name.setCreateTime(new Date());
            container.add(name);
        }
        List<ShardingName> names = null;
        return JSONObject.toJSONString(names);
    }

    @GetMapping("/find")
    @ResponseBody
    public String doFind(@RequestParam(value = "start") Integer start, @RequestParam(value = "end") Integer end) {

        List<Integer> find = new ArrayList<>();
        for (int i = start; i < end; i++) {
            find.add(i);
        }

        List<ShardingName> names = nameRepository.findAllById(find);
        return JSONObject.toJSONString(names);
    }

    @GetMapping("/findAll")
    @ResponseBody
    public String doFind() {

        List<ShardingName> names = nameRepository.findAll();
        return JSONObject.toJSONString(names);
    }
}
