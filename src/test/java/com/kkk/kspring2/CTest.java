package com.kkk.kspring2;

import org.junit.jupiter.api.Test;

import java.util.*;

public class CTest {
    @Test
    public void coTest_10() {
//        String[] args1 = {"leo", "kiki", "eden"};
//        String[] args2 = {"eden", "kiki"};
        String[] args1 = {"mislav", "stanko", "mislav", "ana"};
        String[] args2 = {"stanko", "ana", "mislav"};
        String lRet = solution_11(args1, args2);

        System.out.println("## Result : "+ lRet);
    }

    public String solution(String[] participant, String[] completion) {
        String answer = "";

        List<String> pList = new ArrayList<>(Arrays.asList(participant));
        List<String> cList = new ArrayList<>(Arrays.asList(completion));
        for (int i=0; i<pList.size(); i++) {
            int j;
            int cSize = cList.size();
            for (j=0; j<cSize; j++) {
                if (pList.get(i).equals(cList.get(j))) {
                    pList.remove(i); i--;
                    cList.remove(j);
                    break;
                }
            }
            if (j == cSize) {
                answer += pList.get(i);
                break;
            }
        }

        return answer;
    }

    // 해시맵
    public String solution_11(String[] participant, String[] completion) {
        String answer = "";

        HashMap<String, Integer> hm = new HashMap<>();
        for (String player : participant) hm.put(player, hm.getOrDefault(player, 0) + 1);
        for (String player : completion) hm.put(player, hm.get(player) - 1);

        for (String key : hm.keySet()) {
            if (hm.get(key) != 0){
                answer = key;
            }
        }

        return answer;
    }

    // 소팅 (Best) : Arrays.sort
    public String solution_12(String[] participant, String[] completion) {
        String answer = "";

        Arrays.sort(participant);
        Arrays.sort(completion);
        int i;
        for ( i=0; i<completion.length; i++) {
            if (!participant[i].equals(completion[i])){
                return participant[i];
            }
        }
        return participant[i];

//        return answer;
    }

    @Test
    public void coTest_20() {
        int[] args1 = {1, 5, 2, 6, 3, 7, 4};
        int[][] args2 = {{2, 5, 3}, {4, 4, 1}, {1, 7, 3}};

        int[] lRet = solution_21(args1, args2);
//        List<Integer> lRetList = new ArrayList<>(lRet);

        System.out.println("## Result : "+ lRet[0]+lRet[1]+lRet[2]);
    }

    // Arrays.copyOfRange
    public int[] solution_21(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        int a = 0;
        for(int[] info : commands){
            int i = info[0];
            int j = info[1];
            int k = info[2];

            int[] buf = Arrays.copyOfRange(array,i-1,j);
            Arrays.sort(buf);
            answer[a] = buf[k-1];
            a++;
        }

        return answer;
    }

    @Test
    public void coTest_30() {
//        int[] args1 = {93, 30, 55};
//        int[] args2 = {1, 30, 5};
        int[] args1 = {95, 90, 99, 99, 80, 99};
        int[] args2 = {1, 1, 1, 1, 1, 1};

        int[] lRet = solution_31(args1, args2);

        System.out.println("## Result : "+ lRet[0]+lRet[1]);
    }

    public int[] solution_31(int[] progresses, int[] speeds) {
//        int[] answer = {};
        Queue<Integer> q = new LinkedList<>();
        List<Integer> answerList = new ArrayList<>();

        for (int i = 0; i < speeds.length; i++) {
            double remain = (100 - progresses[i]) / (double) speeds[i];
            int date = (int) Math.ceil(remain);

            if (!q.isEmpty() && q.peek() < date) {
                answerList.add(q.size());
                q.clear();
            }

            q.add(date);
        }

        answerList.add(q.size());

        int[] answer = new int[answerList.size()];

        for (int i = 0; i < answer.length; i++) {
            answer[i] = answerList.get(i);
        }

        return answer;
    }


    @Test
    public void coTest_40() {
        int[][] args1 = {{1,2,1},{2,3,1},{3,2,1}};

        int[][] lRet = solution_41(args1);

//        System.out.println("## Result : "+ lRet[0]+lRet[1]);
    }

    public int[][] solution_41(int[][] matrix) {
        int xSize = matrix.length;
        int ySize = matrix[0].length;
        int[][] answer = new int[xSize][ySize];

        for (int i=0; i<xSize; i++) {
            for (int j=0; j<ySize; j++) {

                for (int k=0; k<xSize; k++) {
                    for (int l=0; l<ySize; l++) {
                        if (i == k || j == l)
                            answer[i][j] += matrix[k][l];
                    }
                }
            }
        }

        return answer;
    }

    @Test
    public void coTest_50() {
//        String args1 = "UUUUU";
        String args1 = "Aaaa+!12-3";
        int[] lRet = solution_51(args1);

        System.out.println("## Result : "+ lRet[0]+lRet[1]);
    }

    public int[] solution_51(String inp_str) {
        List<Integer> lList = new ArrayList<>();

        // #1
        int lLen = inp_str.length();
        if (lLen < 8 || lLen > 15) lList.add(1);

        // #2
        byte[] lBytes = inp_str.getBytes();
        int[] flag3 = new int[4];
        boolean lFlag2 = false;
        for (int i=0; i<lBytes.length; i++) {
            byte c = lBytes[i];
            if ( c >= 'A' && c <= 'Z' ) {
                flag3[0]++; continue;
            }
            if ( c >= 'a' && c <= 'z' ) {
                flag3[1]++; continue;
            }
            if ( c >= '0' && c <= '9' ) {
                flag3[2]++; continue;
            }
            if ( c=='~' || c=='!' || c=='@' || c=='#' || c=='$' || c=='%' || c=='^' || c=='&' || c=='*') {
                flag3[3]++; continue;
            }
            lFlag2 = true;
        }
        if (lFlag2) lList.add(2);

        // #3
        int flagVal = 0;
        if (flag3[0] > 0) flagVal++;
        if (flag3[1] > 0) flagVal++;
        if (flag3[2] > 0) flagVal++;
        if (flag3[3] > 0) flagVal++;
        if (flagVal < 3) lList.add(3);

        // #4
        int lSeq = 1;
        for (int i=0; i<lBytes.length; i++) {
            if (i == 0) continue;
            if (lBytes[i]==lBytes[i-1]) lSeq++;
            else lSeq = 1;

            if (lSeq >= 4) {
                lList.add(4);
                break;
            }
        }

        // #5
        Byte[] lTmp = new Byte[lBytes.length];
        for (int i=0; i<lTmp.length; i++) lTmp[i] = lBytes[i];
        HashMap<Byte, Integer> hm = new HashMap<>();
        for (Byte player : lTmp) hm.put(player, hm.getOrDefault(player, 0) + 1);
        for (Byte key : hm.keySet()) {
            if (hm.get(key) > 2){
                lList.add(5);
            }
        }
        int[] answer = new int[lList.size()];
        for (int i=0; i<answer.length; i++) {
            answer[i] = lList.get(i);
        }
        return answer;

    }

}

