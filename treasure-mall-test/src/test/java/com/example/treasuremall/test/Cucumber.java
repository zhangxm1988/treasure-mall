package com.example.treasuremall.test;

import com.example.treasuremall.start.TreasureMallApplication;
import com.example.treasuremall.api.facade.ItemQueryFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TreasureMallApplication.class)
public class Cucumber {

    @Resource
    private ItemQueryFacade itemQueryFacade;

    @Test
    public void say() {
        itemQueryFacade.queryItemById(1);
    }

}