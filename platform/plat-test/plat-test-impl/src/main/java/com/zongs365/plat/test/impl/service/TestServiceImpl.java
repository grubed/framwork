package com.zongs365.plat.test.impl.service;



import com.zongs365.center.diboot.api.CenterService;
import com.zongs365.center.test.api.UserService;
import com.zongs365.plat.test.api.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;

@Slf4j
@org.apache.dubbo.config.annotation.Service(protocol = "dubbo", version = "1.0.0")
public class TestServiceImpl implements TestService {
    @Reference(protocol = "dubbo", version = "1.0.0")
    private UserService userService;

    @Reference(protocol = "dubbo", version = "1.0.0")
    private CenterService centerService;

//    @Reference(protocol = "dubbo", version = "1.0.0")
//    private AscdeService iAscdeService;

    @Override
    public String get(Integer id) {
        log.info("TestServiceImpl home");
        centerService.test();
        return userService.get(id);
    }

//
//    public String Test() {
//        log.info("TestServiceImpl home");
//        iAscdeService.getList();
//        return "";
//    }
}
