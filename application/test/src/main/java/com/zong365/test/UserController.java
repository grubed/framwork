package com.zong365.test;


import com.zongs365.center.diboot.api.CenterService;
import com.zongs365.center.test.api.UserService;
import com.zongs365.plat.test.api.TestService;
import org.apache.dubbo.config.annotation.Reference;
import com.zongs365.web.annotation.CheckToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@CheckToken
public class UserController {

    @Reference(protocol = "dubbo", version = "1.0.0")
    private CenterService testService;

    @GetMapping("/get")
    public String  get(@RequestParam("id") Integer id) throws Exception {
        log.info("Handling home");

        return testService.test().toString();
    }


//    @GetMapping("/test")
//    public String  test() {
//
//        log.info("Handling home");
//        return "";
//    }
//    @GetMapping("/test2")
//    public String  test2() {
//
//        log.info("Handling home2");
//        log.info(testService.Test());
//        return "";
//    }
}
