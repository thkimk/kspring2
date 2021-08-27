package com.kkk.kspring2.stock;

import com.kkk.kspring2.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class StockData implements Serializable {
    private static final long serialVersionUID = -4865946674835353955L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    int id;
    StockItem item = null;
    List<StockUnit> data = new ArrayList<>();

    int currValue;
    int currVolume;
    int minValue=100000000;   // 종가기준
    int maxValue=0;   // 종가기준
    int minVolume=100000000;
    int maxVolume=0;
    int avgVolume=0;
    int avgValue=0;

    // 최종 스코어
    float score;

    // 특수 정보들
    boolean isNew = false;
    String isNewDate = null;
    boolean isStop = false;
    String isStopDate = null;
    boolean isSkip = false;

    //
    float volumeMaxRate = 0;
    int volumeMaxPos = 0;

/*
    // 스코어 기준
    float widthValue;    // (max/min) : 클수록 좋음
    float currValuePos;  // (curr-min)/(max-min) : 작을수록 좋음 (0.1)
    float currValuePos10;   // 최근 10일 위치 (CP_BASE)

    // 변화량 : 클수록 좋음
    float ratioHL=0;   // sum(고가/저가)
    float ratioCC=0;   // sum(abs(1-종가1/종가2))
    float ratioCO=0;   // sum(abs(1-종가/시가))
    float ratioVV=0;   // sum(abs(1-거래량1/거래량2))
    float ratioOL=0;   // sum(시가/저가) : 클수록 좋음

    // 최근 변화량
    float ratioHL2=0;   // 최근 10일
    float ratioCO2=0;   // 최근 10일
    float ratioOL2=0;   // 최근 10일
    float ratioVV2=0;   // 최근 10일
    float ratioVV3=0;   // 최근 10일과 과거 50일간의 차이
    float ratioCC2=0;   // 최근 10일 (Deprecated)
    float ratioLC2=0;   // 최근 10일
    float ratioHC=0;   // 최근 10일

    // 최종
    float ratioLL=0;
    boolean isFlag1 = true;
    boolean isFlagDown = false;
    boolean isFlagClose = false;
    boolean isFlagVolume = false;

    boolean isUp = true;
    int isUp1 = 0;       // +이면 UP, -이면 DOWN
    int isUp2 = 0;      // +이면 UP, -이면 DOWN (조더 과거) --> isUp2를 보고, isUp의 방향석 예측
                        // isUp2가 down이고, isUp이 down이면 곧 Up

    // 보합
    float steadyValue=0;    // 작을수록 좋음
    float descVolume=0;     // 클수록 좋음

    // 스코어 (순위기준)
    int rankWidthValue;
    int rankCurrValuePos;
    int rankCurrValuePos10;
    int rankRatioHL;
    int rankRatioCC;
    int rankRatioCO;
    int rankRatioVV;
    int rankRatioOL;
    int rankSteadyValue;
    int rankDescVolume;
    int rankRatioHL2;
    int rankRatioCO2;
    int rankRatioOL2;
    int rankRatioVV2;
    int rankRatioVV3;
    int rankRatioCC2;
    int rankRatioLC2;
    int rankRatioHC;
    int rankRatioLL;

    float ratioCC01=0; // 클수록
    float ratioOH01=0; // 클수록
    float ratioCC02=0; // 작을수록
    float ratioOL02=0; // 클수록

    float ratioCC11=0; // 작을수록
    float ratioHL11=0; // 작을수록
    float ratioCC12=0; // 클수록
    float ratioHL12=0; // 클수록

    float ratioC4C21=0; // 작을수록
    float ratioCC22=0;  // 작을수록
    float ratioVV22=0;  // 클수록
    float ratioOL22=0;  // 클수록
    float ratioOH22=0;  // 작을수록
    float ratioCC23=0;  // 클수록 or 0에 가까울수록
    float ratioVV23=0;  // 작을수록
    float ratioOH23=0;  // 클수록

    int rankRatioC4C21=0; // 작을수록
    int rankRatioCC22=0;  // 작을수록
    int rankRatioVV22=0;  // 클수록
    int rankRatioOL22=0;  // 클수록
    int rankRatioOH22=0;  // 작을수록
    int rankRatioCC23=0;  // 클수록 or 0에 가까울수록
    int rankRatioVV23=0;  // 작을수록
    int rankRatioOH23=0;  // 클수록

    int rankRatioCC01; // 클수록
    int rankRatioOH01; // 클수록
    int rankRatioCC02; // 작을수록
    int rankRatioOL02; // 클수록

    int rankRatioCC11; // 작을수록
    int rankRatioHL11; // 작을수록
    int rankRatioCC12; // 클수록
    int rankRatioHL12; // 클수록
*/



    public StockData(StockItem item) {
        this.item = item;
    }

    public void setCrawlingData(Elements trs) {
        for (int i=0; i<trs.size(); i++) {
            StockUnit lUnit = new StockUnit();
            lUnit.setAllData(trs.get(i).select("td"));
            if (i == 0) {
                currValue = lUnit.getCloseValue();
                currVolume = lUnit.getVolume();
            }

            data.add(lUnit);
        }
    }

    public void setAllData() {
        int lValueSum=0, lVolumeSum=0, lVolumeMax=0;
        int lSize = 20; // data.size();
        for (int i=0; i<lSize; i++) {
            StockUnit lUnit = data.get(i);
            StockUnit lUnitOld = data.get(i+1);

            int lOpen = lUnit.getOpenValue();
            int lClose = lUnit.getCloseValue();
            int lVolume = lUnit.getVolume();

            if (i == 0) {
                currValue = lOpen;
                currVolume = lVolume;
            }

            if (lVolume<Constants.SKIP_VOLUME_MIN ||
                    lOpen<Constants.SKIP_VALUE_MIN || lOpen>Constants.SKIP_VALUE_MAX) {
                isSkip = true;
            }

            // 신규 상장 종목입니다.
            if (lUnit.getDate() == null) {
                isNew = true;
                isNewDate = lUnit.getDate();
                break;
            }

            if (lVolumeMax < lVolume) {
                lVolumeMax = lVolume;
                volumeMaxPos = i;
            }

            lValueSum += lClose;
            lVolumeSum += lVolume;
        }

        avgVolume = lVolumeSum / lSize;
        avgValue = lValueSum / lSize;
        volumeMaxRate = (avgVolume==0? 0f : ((float)lVolumeMax / avgVolume));

        if (isNew) logger.info(" --> 신규상장 종목 : {} / {}", item.getName(), isNewDate);
        else if (isSkip) logger.info(" --> 미대상 종목 : {} / {}원 / {}건", item.getName(), currValue, currVolume);
    }

    public void setAllData2nd() {
/*        StockUnit lUnit = null;
        StockUnit lUnitOld = null;
        int baseVolumeSum=0;
        int currValue2=0;
        int lVol3=0;
        float ratioCC3=0, ratioCCSum=0;
        int lFlagClose = 0, lFlagDown = 0, lFlagVolume = 0;

        // 스코어 values
        boolean lisFlag1 = false;
        for (int i = 0; i<data.size(); i++) {
            lUnit = data.get(i);
            if (i<(data.size()-1)) lUnitOld = data.get(i+1);
            baseVolumeSum += lUnit.getVolume();

            // 변수 초기값
            if (i == 0) {
                currValue2 = lUnit.getCloseValue();
            }

            // 변수 갱신
            int lOpen = lUnit.getOpenValue();
            int lClose = lUnit.getCloseValue();
            int lVolume = lUnit.getVolume();
            if (lClose < minValue) minValue = lClose;
            if (lClose > maxValue) maxValue = lClose;
            if (lVolume < minVolume) minVolume = lVolume;
            else if (lVolume > maxVolume) maxVolume = lVolume;

        }*/

    }

    // 변화율을 1.5 이하로 계산 --> 1.5 초과는 무조건 1.5로 제한
    private float calRatio(float v1, float v2) {
        float lRet = v1>v2 ? v1/v2 : v2/v1;
        if (lRet > 1.05) return (float)1.05;
        if (Float.isNaN(lRet)) return 1;

        return lRet;
    }
    private float calRatio2(float v1, float v2) {
        float lRet = v1>v2 ? v1/v2 : v2/v1;
        if (lRet > 1.1) return (float)1.1;
        if (Float.isNaN(lRet)) return 1;

        return lRet;
    }
    private float calRatioVol(float v1, float v2) {
        float lRet = v1>v2 ? v1/v2 : v2/v1;
        if (lRet > 1.5) return (float)1.5;
        if (Float.isNaN(lRet)) return 1;

        return lRet;
    }
    private float calRatio3(float v1, float v2) {
        float lRet = v1/v2;
        if (lRet > 1.1) return (float)1.1;
        if (Float.isNaN(lRet)) return 1;

        return lRet;
    }

    // 최종 스코어 계산 (분자가 클수록 비중이 큰거임)
    public void calScore() {
//        score = 100000
//                -rankWidthValue*Constants.SCORE_WV - rankCurrValuePos*Constants.SCORE_CP
//                -rankRatioHL*Constants.SCORE_HL - rankRatioCC*Constants.SCORE_CC -rankRatioCO*Constants.SCORE_CO - rankRatioVV*Constants.SCORE_VV - rankRatioOL*Constants.SCORE_OL
//                -rankRatioHL2*Constants.SCORE_HL2 - rankRatioCO2*Constants.SCORE_CO2 -rankRatioOL2*Constants.SCORE_OL2 -rankRatioVV2*Constants.SCORE_VV2 -rankRatioCC2*Constants.SCORE_CC2
//                -rankSteadyValue*Constants.SCORE_SV - rankDescVolume*Constants.SCORE_DV;
    }


    public void writeExcelHeader(XSSFRow curRow) {
        curRow.createCell(0).setCellValue("코드");
        curRow.createCell(1).setCellValue("이름");
        curRow.createCell(2).setCellValue("신규");
        curRow.createCell(3).setCellValue("중지");

        curRow.createCell(4).setCellValue("현재가");
        curRow.createCell(5).setCellValue("현재거래량");
        curRow.createCell(6).setCellValue("최저가");
        curRow.createCell(7).setCellValue("최고가");
        curRow.createCell(8).setCellValue("최저거래량");
        curRow.createCell(9).setCellValue("최고거래량");

        curRow.createCell(10).setCellValue("스코어");

        curRow.createCell(11).setCellValue("저평가");
        curRow.createCell(12).setCellValue("게시판");
        curRow.createCell(13).setCellValue("기업정보");
    }

    public void writeExcelRow(XSSFRow curRow, String PPer, String PBoard, String PCo) {
        curRow.createCell(0).setCellValue(item.getCode());
        curRow.createCell(1).setCellValue(item.getName());
        curRow.createCell(2).setCellValue(isNew);
        curRow.createCell(3).setCellValue(isStop);

        curRow.createCell(4).setCellValue(currValue);
        curRow.createCell(5).setCellValue(currVolume);
        curRow.createCell(6).setCellValue(minValue);
        curRow.createCell(7).setCellValue(maxValue);
        curRow.createCell(8).setCellValue(minVolume);
        curRow.createCell(9).setCellValue(maxVolume);

        curRow.createCell(10).setCellValue(score);

        curRow.createCell(11).setCellValue(PPer);
        curRow.createCell(12).setCellValue(PBoard);
        curRow.createCell(13).setCellValue(PCo);
    }

}
