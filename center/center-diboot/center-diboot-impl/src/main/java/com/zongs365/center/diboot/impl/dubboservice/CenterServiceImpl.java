package com.zongs365.center.diboot.impl.dubboservice;

import com.zongs365.center.diboot.api.CenterService;
import com.zongs365.center.diboot.api.TestAscde;
import com.zongs365.center.diboot.impl.service.TestAscdeService;

import javax.annotation.Resource;



@org.apache.dubbo.config.annotation.Service(protocol = "dubbo", version = "1.0.0")
public class CenterServiceImpl implements CenterService {
    @Resource
    private TestAscdeService testAscdeService;

    @Override
    public TestAscde test() {
//        throw new BusinessException();
        TestAscde testAscde = new TestAscde();
        testAscde.setName("1");
        testAscdeService.createEntity(testAscde);
        return testAscde;
    }
}
