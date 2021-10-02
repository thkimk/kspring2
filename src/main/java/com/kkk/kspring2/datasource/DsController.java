package com.kkk.kspring2.datasource;

import com.kkk.kspring2.datasource.defaultmaps.DefaultMapper;
import com.kkk.kspring2.datasource.primarymaps.PrimaryMapper;
import com.kkk.kspring2.datasource.secondmaps.SecondMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ds")
public class DsController {
//    @Autowired
//    DefaultMapper defaultMapper;

    @Autowired
    PrimaryMapper primaryMapper;

    @Autowired
    SecondMapper secondMapper;

    @RequestMapping("/test0")
    public String test0() {
//        System.out.println("## test: [1]");
//        String lVal = defaultMapper.test();
//
//        System.out.println("## test: [2] "+ lVal);

        String lVal = "default";
        return lVal;
    }

    @RequestMapping("/test1")
    public String test1() {
        System.out.println("## test: [1]");
        String lVal = primaryMapper.test();

        System.out.println("## test: [2] "+ lVal);

//        String lVal = "primary";
        return lVal;
    }

    @RequestMapping("/test2")
    public String test2() {
        System.out.println("## test: [1]");
        String lVal = secondMapper.test();

        System.out.println("## test: [2] "+ lVal);
        return lVal;
    }
}
