package logic;

import data.DataService;
import java.util.ArrayList;

import java.util.List;

/**
 * Ez az osztaly ellenorzi a tipp eredmelyet
 *
 * @author gabor
 */
public class ListCheck {

    /**
     *
     * @param pcNumberList
     * @param gamerNumberList
     * @return black=jo es jo helyen white = jo de rossz helyen
     */
    public static int[] listCheck(List<Integer> pcNumberList, List<Integer> gamerNumberList) {

        int black = 0;
        int white = 0;

        //feketek
        List<Integer> tmpPcNumList = new ArrayList<>(pcNumberList);
        List<Integer> tmpPlayerList = new ArrayList<>(gamerNumberList);

       
        for (int i = 0; i < gamerNumberList.size(); i++) {
            if (tmpPcNumList.get(i) == tmpPlayerList.get(i)) {
                tmpPcNumList.set(i, -1);
                tmpPlayerList.set(i, -10);
                black++;
                if(black==DataService.getPointnumber())
                    return new int[]{black,white};
            }

        }
        //feherek

        whitecheck:
        for (int plListIndex = 0; plListIndex < tmpPlayerList.size();) {
            if(tmpPlayerList.get(plListIndex)<0){
                plListIndex++;
                if(plListIndex==tmpPlayerList.size()) break;
            }
            for (int pcListIndex = 0; pcListIndex < tmpPcNumList.size();) {
                
                
                if (tmpPlayerList.get(plListIndex) == tmpPcNumList.get(pcListIndex)) {
                    tmpPcNumList.set(pcListIndex, -5);
                    //tmpPlayerList.set(pl, -6);
                    white++;
                    plListIndex++;
                    if(plListIndex==tmpPlayerList.size()) break whitecheck;

                    
                        
                    pcListIndex = 0;
                } else {
                    pcListIndex++;
                }

            }
            plListIndex++;

        }

        System.out.println(black + " " + white);
        return new int[]{black, white};
    }

    private ListCheck() {
    }
}
