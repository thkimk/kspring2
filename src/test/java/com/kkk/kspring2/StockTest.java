package com.kkk.kspring2;

import com.kkk.kspring2.stock.StockData;
import com.kkk.kspring2.stock.StockDatas;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class StockTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

/*
    @BeforeClass
    public static void beforeClass() {
        System.out.println("==== Before this class ====\n");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("==== After this Class ====\n");
    }

    @Before
    public void before() {
        System.out.println("==== Before every test ====");
    }

    @After
    public void after() {
        System.out.println("==== After every test ====\n");
    }
*/

    @Test
    public void dummyTest() {
        System.out.println("Test is testing now...");
    }


    @Test
    public void collectStocks() {
        StockDatas lDatas = new StockDatas();
        lDatas.collectCrawlings();
    }

    @Test
    public void scoreStocks() {
        StockDatas lStockDatas = StockDatas.serialFromFile();
        lStockDatas.collectDatas(0);
        List<StockData> lDatas0 = lStockDatas.score2();

        lStockDatas = StockDatas.serialFromFile();
        lStockDatas.collectDatas(1);
        List<StockData> lDatas1 = lStockDatas.score2();

        for (StockData data0 : lDatas0) {
            logger.info("#### [0] {}({}) : Score {} - 거래량 위치/min비율, 10일간 상태 {}/{}, {}", data0.getItem().getName(), data0.getItem().getCode(), data0.getScore(), data0.getVolumeMinPos(), data0.getVolumeMinRate(), (data0.getRate10days()-1)*100);
            for (StockData data1 : lDatas1) {
                if (data0.getItem().getCode().equals(data1.getItem().getCode())) {
                    logger.info("######## [1] {}({}) : Score {} - 거래량 위치/min비율, 10일간 상태 {}/{}, {}", data0.getItem().getName(), data0.getItem().getCode(), data0.getScore(), data0.getVolumeMinPos(), data0.getVolumeMinRate(), (data0.getRate10days()-1)*100);
                }
            }
        }

/*
        // 점수(Score) 매기기 (점수가 가장 높은 종목순으로 소팅)
        StockDatas lStockDatas2 = StockDatas.serialFromFile();
        lStockDatas2.collectDatas();
        lStockDatas2.score7();
*/
        logger.info("#### Completed..");

        // 기업개요

    }
    @Test
    public void scoreStocks2() {
        StockDatas lStockDatas = StockDatas.serialFromFile();
        lStockDatas.collectDatas();
        lStockDatas.score2();

/*
        // 점수(Score) 매기기 (점수가 가장 높은 종목순으로 소팅)
        StockDatas lStockDatas2 = StockDatas.serialFromFile();
        lStockDatas2.collectDatas();
        lStockDatas2.score7();
*/
        logger.info("#### Completed..");

        // 기업개요

    }
    @Test
    public void scoreStocks3() {
        StockDatas lStockDatas = StockDatas.serialFromFile();
        lStockDatas.collectDatas();
        lStockDatas.scoreExcel();

        logger.info("#### Completed..");

    }

    @Test
    public void scoreStocks_old() {
        StockDatas lTmp = new StockDatas();
        StockDatas lStockDatas = lTmp.serialFromFile();
        List<StockData> lDatas = lStockDatas.getDatas();

        lStockDatas.collectDatas();

        // 점수(Score) 매기기 (점수가 가장 높은 종목순으로 소팅)
        lStockDatas.score();
        logger.info("#### Completed..");

        // 기업개요

    }

}