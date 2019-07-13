/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import data.DataService;

/**
 *
 * @author gabor
 */
public class EndCheck {

    /**
     *
     * @param chkPoint :talalatok tombje []{black,white}
     * @param componentCount : a felrakott jatekkepek szama
     * @return 1=telitalalat,negy fekete,0 =folytatodik a jatek,-1=vesztes jatek
     * 
     */
    public static int endCheck(int[] chkPoint, int componentCount) {
        
        if(chkPoint[0]==DataService.getPointnumber()) return 1;
        if(componentCount==DataService.getGameImageinRow()*DataService.maxTippRow()) return -1;
        return 0;
       
    }

    public static int endCheck(int componentCount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private EndCheck() {
    }
    
    
    
}
