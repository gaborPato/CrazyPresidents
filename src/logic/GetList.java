/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import data.DataService;
import java.util.List;
import java.util.Random;
import interfaces.MakeGameNumbers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import java.util.Set;

/**
 *
 * @author gabor
 */
public class GetList {

    private static MakeGameNumbers mgnPC;
    private static MakeGameNumbers mgnGamer;

    private GetList() {
    }

    static {
        mgnPC = (String fourNumber, int playMode) -> {
            
            

            if (playMode < 1) {
                Set<Integer> pcNumberList = new LinkedHashSet<>();
                for (; pcNumberList.size() < DataService.getGameImageinRow();) {
                    pcNumberList.add(new Random().nextInt(DataService.getAllImagesInGame()));

                }
                System.out.println(pcNumberList);
                return new ArrayList<>(pcNumberList);
            }
                  List<Integer> pcNumberList = new ArrayList<>();
            for (int i = 0; i < DataService.getGameImageinRow(); i++) {
                pcNumberList.add(new Random().nextInt(DataService.getAllImagesInGame()));
                
            }
            return pcNumberList;
            

        };
        mgnGamer = (String fourNumber, int playMode) -> {
            String[] split = fourNumber.split("");
            Integer[] intArray = new Integer[split.length];
            for (int i = 0; i < split.length; i++) {
                intArray[i] = Integer.valueOf(split[i]);
            }


            return Arrays.asList(intArray);
        };
    }

    public static MakeGameNumbers getMgnPC() {
        return mgnPC;
    }

    public static MakeGameNumbers getMgnGamer() {
        return mgnGamer;
    }

    public static List<Integer> doList(MakeGameNumbers g, String fourNumber, int playMode) {
        return g.makeGameList(fourNumber, playMode);
    }
}
