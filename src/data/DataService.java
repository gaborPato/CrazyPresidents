/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author gabor
 */
public  class DataService {
    public static final String getSoundpath(){
        return "Sound";
    }

    public static final String getMusicOFFImagePath() {
        return "Pictures/MusIcon/musicoffbutton2x.png";
    }

    public static final String getMusicONImagePath() {
        return "Pictures/MusIcon/musiconbutton2x.png";
    }

    /**
     *
     * @return :pontozasra hasznalt kepek szama
     */
    public final static int getPointImagesValue() {
        return 3;
    }

    /**
     *
     * @return : kiadott pontokszama soronkent
     */
    public final static int getPointnumber() {
        return 4;
    }

    /**
     *
     *
     * @return : egy sorban a tippek szama
     */
    public final static int getGameImageinRow() {
        return 4;
    }

    /**
     *
     * @return : ozzes tippsor szama
     */
    public final static int maxTippRow() {
        return 8;
    }

    /**
     *
     * @return all images of game
     */
    public final static int getAllImagesInGame() {
        return 6;
    }

    public final static String getInformationPath() {
        return "Inf/inf.pdf";
    }

    public final static String getOKButtonIconPath() {
        return "Pictures/kutya.png";
    }

    private DataService() {
    }

}
