package com.kkk.kspring2;

import com.kkk.kspring2.stock.StockData;
import com.kkk.kspring2.stock.StockDatas;
import com.kkk.kspring2.stock.StockItem;
import com.kkk.kspring2.stock.StockUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

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
        lStockDatas.collectDatas(1);
        List<StockData> lDatas1 = lStockDatas.score2();

        for (StockData data1 : lDatas1) {
            StockItem lItem = data1.getItem();
            logger.info("#### [0] {}({}) : Score {} - 거래량 위치/min비율, 10일간 상태 {}/{}, {}", lItem.getName(), lItem.getCode(), data1.getScore(), data1.getVolumeMinPos(), data1.getVolumeMinRate(), (data1.getRate10days()-1)*100);

        }

    }

    @Test
    public void scoreStocks4() {
        StockDatas lStockDatas = StockDatas.serialFromFile();
        lStockDatas.collectDatas(0);
        List<StockData> lDatas0 = lStockDatas.score2();

        lStockDatas = StockDatas.serialFromFile();
        lStockDatas.collectDatas(1);
        List<StockData> lDatas1 = lStockDatas.score2();

        for (StockData data0 : lDatas0) {
            StockItem lItem = data0.getItem();
            logger.info("#### [0] {}({}) : Score {} - 거래량 위치/min비율, 10일간 상태 {}/{}, {}", lItem.getName(), lItem.getCode(), data0.getScore(), data0.getVolumeMinPos(), data0.getVolumeMinRate(), (data0.getRate10days()-1)*100);

            for (StockData data1 : lDatas1) {
                if (lItem.getCode().equals(data1.getItem().getCode())) {
                    logger.info("######## [1] {}({}) : Score {} - 거래량 위치/min비율, 10일간 상태 {}/{}, {}", lItem.getName(), lItem.getCode(), data0.getScore(), data0.getVolumeMinPos(), data0.getVolumeMinRate(), (data0.getRate10days()-1)*100);
                }
            }

            StockUnit lUnit = data0.getData().get(0);
            float lWidth = (float)lUnit.getHighValue()/lUnit.getLowValue();
            if (lWidth>1.015 && lWidth<1.025) {
                logger.info("######## [2] {}({}) : Score {} - 거래량 위치/min비율, 10일간 상태 {}/{}, {}", lItem.getName(), lItem.getCode(), data0.getScore(), data0.getVolumeMinPos(), data0.getVolumeMinRate(), (data0.getRate10days()-1)*100);
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

    @Test
    public void calBunsan() {
        Random rand = new Random();
//        int[] intArr = new int[5]; // 정수 5개 담는 배열
        int sum = 0; // 평균을 구하기 위해 합계변수 설정
        double dev = 0; // 분산, 표준편차를 위해 편차변수 설정
        double devSqvSum = 0; // 편차제곱합
        double avg; // 평균
        double var; // 분산
        double std; // 표준편차

        // 1 ~ 100 범위의 난수 배열원소 갯수만큼 생성
/*
        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = rand.nextInt(100) + 1; // 100도 포함됨. 난수 도출
            sum += intArr[i]; // 합계에 누적시킴
            System.out.print(intArr[i] + " "); // 난수 출력
        } // end for
*/

        int[] intArr = {3, 3, 3, 3, 3};
        for (int i=0; i<intArr.length; i++) sum += intArr[i];
        avg = sum / intArr.length; // 평균 도출

        for (int i = 0; i < intArr.length; i++) {
            dev = (intArr[i] - avg); // 편차를 구하고,
            devSqvSum += Math.pow(dev, 2); // 편차제곱합에 누적시킴
        } // end for

        var = devSqvSum / intArr.length; // 분산 도출
        std = Math.sqrt(var); // 표준편차 도출

        System.out.print("\n평균:" + avg + "\n분산:" + var +
                "\n표준편차:" + std ); // 평균, 분산, 표준편차 출력
    }


    @Test
    public void testException() {
        try {
            String lRet = testExcept1(100);
            System.out.println("finish : " + lRet);
        } catch (Exception e) {
            System.out.println("exception-main: "+ e.getMessage());
        }
    }

    private String testExcept1(int flag1) {
        String lRet = "ret ok: ";
        try {
            if (flag1 == 10) {
                throw new Exception();
            } else {
                System.out.println("before return: "+ lRet);
                return lRet;
            }
        } catch (Exception e) {
            System.out.println("exception: "+ e.getMessage());
        } finally {
            lRet = "modify";
            System.out.println("finally: "+ lRet);
        }

        return "ok return";
    }

    private String testExcept2(int flag1) throws Exception {
        if (flag1 == 10) {
            throw new Exception();
        } else {
            return "ok";
        }
    }

}