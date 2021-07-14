package com.zongs365.center.test.impl.dubboservice;

import com.zongs365.center.test.api.UserService;
import com.zongs365.center.test.impl.entity.Ascde;
import com.zongs365.center.test.impl.service.AscdeServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@org.apache.dubbo.config.annotation.Service(protocol = "dubbo", version = "1.0.0")
public class UserServiceImpl implements UserService {

    @Resource
    private AscdeServiceImpl ascdeService;

    @Override
    public String get(Integer id) {
        log.info("UserServiceImpl home");
        List<Ascde> ascdeList = ascdeService.list();
        return "user:" + id+ascdeList.size();
    }

}
