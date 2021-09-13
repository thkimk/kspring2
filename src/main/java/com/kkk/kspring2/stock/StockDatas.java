package com.kkk.kspring2.stock;

import com.kkk.kspring2.common.Constants;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
public class StockDatas implements Serializable {
    private static final long serialVersionUID = -4865946674835353965L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    List<StockData> datas = new ArrayList<>();

    // 추천종목 10건 추출 (스코어 계산 후, 스코어로 상위 10건 추출)

    public void serialToFile() {
        try {
            // 파일 직렬화
            Constants.initStockFile();
            FileOutputStream fileStream = new FileOutputStream(Constants.STOCK_FILE);
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(this);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StockDatas serialFromFile() {
        StockDatas lRet = null;
        try {
            Constants.initStockFile();
            FileInputStream fileStream = new FileInputStream(Constants.STOCK_FILE);
            ObjectInputStream os = new ObjectInputStream(fileStream);
            lRet = (StockDatas) os.readObject();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lRet;
    }

    public void collectCrawlings() {
        logger.info("************* doCrawling *************** ");
        String lBaseUrl = "http://finance.naver.com/item/sise_day.nhn?";

        try {
            // 종목 리스트(엑셀)
            StockItems lStockItems = new StockItems();
            List<StockItem> lItems = lStockItems.getItems();

            for (int i=0; i < lItems.size(); i++) {
                StockItem lITem = lItems.get(i);
                StockData lData = new StockData(lITem);
                logger.info("## [testStock] for[{}] {} / {}", i+1, lData.getItem().getCode(), lData.getItem().getName());

                // 1st Basic Data
                // 10개 데이터를 30개 데이터로 늘려 (x3회 looping)
                Elements lAllTrs = new Elements();
                for (int j = 0; j <= Constants.CRAWLING_COUNT; j++) {
                    // 웹크롤링 수행
                    String lUrl = lBaseUrl+ "code="+ lITem.getCode()+ "&page="+ (j+1);
                    Document doc = Jsoup.connect(lUrl).get();
//                    logger.info("## [testStock] Document \n" + doc.toString());

                    // 복수 데이터 추출
                    Elements lTrs = doc.select("tr[onmouseover^='mo']");
//                    logger.info("## [testStock] Elements \n" + lTrs.toString());
                    if (lTrs.size() != 10) {
                        logger.error("## [testStock] crawlling size in Not 10..");
                        throw new Exception();
                    }

                    lAllTrs.addAll(lTrs);
                }

                // 1st/Raw Data
                lData.setCrawlingData(lAllTrs);
                lData.setId(i+1);

                // 1개 종목 완성
                datas.add(lData);
                Thread.sleep(1000);
            }
            serialToFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void collectDatas() {
        for (StockData lData : datas) {
            lData.setAllData(0);
            lData.setAllData2nd();
        }
    }

    public void collectDatas(int p_start) {
        for (StockData lData : datas) {
            lData.setAllData(p_start);
            lData.setAllData2nd();
        }
    }

    public void preScore() {
        int lSize;

        Collections.sort(datas, new Comparator<StockData>() {
            @Override
            public int compare(StockData d1, StockData d2) {
                // d1이 더 크면 1, 같으면 0, 작으면 -1
                if (d1.getMinVolume() == d2.getMinVolume()) return 0;
                return d1.getMinVolume() > d2.getMinVolume()? 1: -1;
            }
        });
        lSize = (int)(datas.size()*0.1);    // 0.2 --> 0.1
        for (int i=0; i<lSize; i++) {
            datas.remove(0);
        }

        Collections.sort(datas, new Comparator<StockData>() {
            @Override
            public int compare(StockData d1, StockData d2) {
                // d1이 더 크면 1, 같으면 0, 작으면 -1
                if (d1.getMinValue() == d2.getMinValue()) return 0;
                return d1.getMinValue() > d2.getMinValue()? 1: -1;
            }
        });
        lSize = (int)(datas.size()*0.1);
        for (int i=0; i<lSize; i++) {
            datas.remove(0);
        }

        Collections.sort(datas, new Comparator<StockData>() {
            @Override
            public int compare(StockData d1, StockData d2) {
                // d1이 더 크면 1, 같으면 0, 작으면 -1
                if (d1.getMaxValue() == d2.getMaxValue()) return 0;
                return d1.getMaxValue() > d2.getMaxValue()? -1: 1;
            }
        });
        lSize = (int)(datas.size()*0.1);
        for (int i=0; i<lSize; i++) {
            datas.remove(0);
        }

    }

    public void score() {
        preScore();

        /*************** #1 : 떨어진다 ******************/
        int lSize = datas.size();
        Collections.sort(datas, new Comparator<StockData>() {
            @Override
            public int compare(StockData d1, StockData d2) {
                // d1이 더 크면 1, 같으면 0, 작으면 -1
                if (d1.getVolumeMaxRate() == d2.getVolumeMaxRate()) return 0;
                return d1.getVolumeMaxRate() > d2.getVolumeMaxRate()? 1: -1;
            }
        });
        for (int i=0; i<lSize; i++) {
            StockData lData = datas.get(0);
            if (lData.getVolumeMaxRate() > 2) break;

            datas.remove(0);
        }

        /*************** #2 : 회복할까? ******************/
//        Collections.sort(datas, new Comparator<StockData>() {
//            @Override
//            public int compare(StockData d1, StockData d2) {
//                // d1이 더 크면 1, 같으면 0, 작으면 -1
//                if (d1.getVolumeMaxPos() == d2.getVolumeMaxPos()) return 0;
//                return d1.getVolumeMaxPos() > d2.getVolumeMaxPos()? 1: -1;
//            }
//        });
        Collections.sort(datas, new Comparator<StockData>() {
            @Override
            public int compare(StockData d1, StockData d2) {
                // d1이 더 크면 1, 같으면 0, 작으면 -1
                if (d1.getVolumeMaxPos() == d2.getVolumeMaxPos()) {
                    if (d1.getVolumeMaxRate() == d2.getVolumeMaxRate()) return 0;
                    return d1.getVolumeMaxRate() > d2.getVolumeMaxRate()? -1: 1;
                }
                return d1.getVolumeMaxPos() > d2.getVolumeMaxPos()? 1: -1;
            }
        });


        /*************** #Summary ******************/
        logger.info("#### "+ Constants.STOCK_FILE);
        int loopCount = datas.size();
        for (int i=0; i< datas.size(); i++) {
            StockData lData = datas.get(i);
            if (lData.isNew()) continue;
            else if (lData.isStop()) continue;
            else {
                System.out.print(lData.getItem().getName()+ ", ");
            }
        }

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(Constants.STOCK_RESULT_FILE);
            XSSFSheet sheet = workbook.createSheet("result");    // sheet 생성

            System.out.print("\n\n");

            int k=0;
            for (int i=0, j=0; i < loopCount; i++) {
                StockData lData = datas.get(i);
                if (j == 0) lData.writeExcelHeader(sheet.createRow(j++));

                if (lData.isNew()) {
//                    logger.info("#### [{}] {} : Score {} - 신규상장 {}", i + 1, lData.getItem().getName(), lData.getScore(), lData.getIsNewDate());
                } else if (lData.isSkip) {
//                    logger.info("#### [{}] {} : Score {} - isSkip {}", i + 1, lData.getItem().getName(), lData.getScore(), lData.getIsStopDate());
                } else {
                    logger.info("#### [{}] {}({}) : Score {} - 거래량 위치/비율 {}/{}", i + 1, lData.getItem().getName(), lData.getItem().getCode(), lData.getScore(), lData.getVolumeMaxPos(), lData.getVolumeMaxRate());
                    String lPer = printPER(lData.getItem().getCode());
                    String lBoard = printBoardInfo(lData.getItem().getCode());
                    String lCo = printCoInfo(lData.getItem().getCode());

                    lData.writeExcelRow(sheet.createRow(j++), lPer, lBoard, lCo);
                    if (++k > 7) {
                        if (lData.getVolumeMaxPos() < 1) continue;
                        break;
                    }
                }
            }

            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<StockData> score2() {
        preScore();

        /*************** #1 : 떨어진다 ******************/
//        int lSize = datas.size();
//        Collections.sort(datas, new Comparator<StockData>() {
//            @Override
//            public int compare(StockData d1, StockData d2) {
//                // d1이 더 크면 1, 같으면 0, 작으면 -1
//                if (d1.getVolumeMaxRate() == d2.getVolumeMaxRate()) return 0;
//                return d1.getVolumeMaxRate() > d2.getVolumeMaxRate()? 1: -1;
//            }
//        });
//        for (int i=0; i<lSize; i++) {
//            StockData lData = datas.get(0);
//            if (lData.getVolumeMaxRate() > 2) break;
//
//            datas.remove(0);
//        }

        /*************** #2 : 회복할까? ******************/
        Collections.sort(datas, new Comparator<StockData>() {
            @Override
            public int compare(StockData d1, StockData d2) {
                // d1이 더 크면 1, 같으면 0, 작으면 -1
                if (d1.getVolumeMinPos() == d2.getVolumeMinPos()) {
                    if (d1.getRate10days() == d2.getRate10days()) return 0;
                    return d1.getRate10days() > d2.getRate10days()? 1: -1;
                }
                return d1.getVolumeMinPos() > d2.getVolumeMinPos()? 1: -1;
            }
        });


        /*************** #Summary ******************/
        logger.info("#### "+ Constants.STOCK_FILE);
        int loopCount = datas.size();
        for (int i=0; i< datas.size(); i++) {
            StockData lData = datas.get(i);
            if (lData.isNew()) continue;
            else if (lData.isStop()) continue;
            else {
                System.out.print(lData.getItem().getName()+ ", ");
            }
        }

        List<StockData> lDatas = new ArrayList<StockData>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(Constants.STOCK_RESULT_FILE);
            XSSFSheet sheet = workbook.createSheet("result");    // sheet 생성

            System.out.print("\n\n");

            int k=0;
            for (int i=0, j=0; i < loopCount; i++) {
                StockData lData = datas.get(i);
                if (j == 0) lData.writeExcelHeader(sheet.createRow(j++));

                if (lData.isNew()) {
//                    logger.info("#### [{}] {} : Score {} - 신규상장 {}", i + 1, lData.getItem().getName(), lData.getScore(), lData.getIsNewDate());
                } else if (lData.isSkip) {
//                    logger.info("#### [{}] {} : Score {} - isSkip {}", i + 1, lData.getItem().getName(), lData.getScore(), lData.getIsStopDate());
                } else {
                    if (lData.getVolumeMinPos() >= 2) break;
                    else if (lData.getRate10days() >= 1.0) continue;
                    else if (k++ > 9) break;

                    lDatas.add(lData);

//                    logger.info("#### [{}] {}({}) : Score {} - 거래량 위치/min비율, 10일간 상태 {}/{}, {}", i + 1, lData.getItem().getName(), lData.getItem().getCode(), lData.getScore(), lData.getVolumeMinPos(), lData.getVolumeMinRate(), (lData.getRate10days()-1)*100);
//                    String lPer = printPER(lData.getItem().getCode());
//                    String lBoard = printBoardInfo(lData.getItem().getCode());
//                    String lCo = printCoInfo(lData.getItem().getCode());

//                    lData.writeExcelRow(sheet.createRow(j++), lPer, lBoard, lCo);
                }
            }

            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lDatas;
    }
    public void scoreExcel() {
        try {
            System.out.print("start..\n\n");

            int lSize = datas.size();
            for (int i=0; i < lSize; i++) {
                int lFlag = 0;
                StockData lData = datas.get(i);
                String lCode = lData.getItem().getCode();

                if (lData.isNew() || lData.isSkip) continue;
                else {
                    String lBaseUrl = "http://finance.naver.com/item/board.nhn?";

                    String lUrl = lBaseUrl+ "code="+ lCode;
                    Document doc = Jsoup.connect(lUrl).get();
                    Elements lElem = doc.select(".section");
                    String lElemStr = lElem.toString();
                    String lOut = "";
                    for (String wording : Constants.BOARD_WORDINGS) {
                        boolean a = lElemStr.contains(wording);
                        if (a) {
                            lFlag++;
                            lOut += wording+ ", ";
                        }
                    }
                    if (lFlag>=1 && lOut != null) System.out.println("[게시판] "+ lData.getItem().getName()+": "+ lOut);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String printCoInfo(String code) {
        String lBaseUrl = "http://finance.naver.com/item/coinfo.nhn?";
        String lRet = "";

        try {
            String lUrl = lBaseUrl+ "code="+ code;
            Document doc = Jsoup.connect(lUrl).get();
            Elements lElems = doc.select("#summary_info");
            Elements lElems2 = lElems.select("p");
            for (Element elem : lElems2) {
                System.out.println("    "+ elem.outerHtml());
                lRet += elem.outerHtml()+ "\n";
            }
        } catch (Exception e) {
            logger.info("#### Error..");
        }

        return lRet;
    }

    String printBoardInfo(String code) {
        String lBaseUrl = "http://finance.naver.com/item/board.nhn?";
        String lRet = "";

        try {
            String lUrl = lBaseUrl+ "code="+ code;
            Document doc = Jsoup.connect(lUrl).get();
            Elements lElem = doc.select(".section");
            String lElemStr = lElem.toString();
            String lOut = null;
            for (String wording : Constants.BOARD_WORDINGS) {
                boolean a = lElemStr.contains(wording);
                if (a) {
                    if (lOut == null) lOut = "";
                    lOut += wording+ ", ";
                }
            }
            if (lOut != null) System.out.println("    [게시판] "+ lOut);
            lRet = lOut;
        } catch (Exception e) {
            logger.info("#### Error..");
        }

        return lRet;
    }



    String printPER(String code) {
        String lBaseUrl = "https://navercomp.wisereport.co.kr/v2/company/c1010001.aspx?cmp_cd=";
        String lRet = "";

        try {
            String lUrl = lBaseUrl + code;
            Document doc = Jsoup.connect(lUrl).get();
            Elements lElems = doc.select("td.cmp-table-cell.td0301").select("dl").select("dt");
            float lPer = 0;
            float lGenPer = 0;
            for (Element lElem : lElems) {
                String lOwnText = lElem.ownText();
                if (lOwnText.equals("PER")) {
                    String lVal = lElem.select("b").get(0).text();
                    if (lVal.equals("N/A")) lPer = 100;
                    else lPer = Float.valueOf(lVal);
                } else if (lOwnText.equals("업종PER")) {
                    String lVal = lElem.select("b").get(0).text();
                    if (lVal.equals("N/A")) lGenPer = -100;
                    else lGenPer = Float.valueOf(lVal);
                }
            }

            lRet = String.valueOf(lGenPer - lPer);
            System.out.println("    [저평가] "+ lRet);
        } catch (Exception e) {
            logger.info("#### Error..");
        }

        return lRet;
    }

    void writeExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(Constants.STOCK_RESULT_FILE);
            XSSFSheet sheet = workbook.createSheet("result");    // sheet 생성
            for (int i=0; i<datas.size(); i++) {
                StockData lData = datas.get(i);
                if (i == 0) lData.writeExcelHeader(sheet.createRow(i));

//                lData.writeExcelRow(sheet.createRow(i+1));
            }

            workbook.write(fos);
            fos.close();
        } catch (Exception e) {}
    }

}
