package data;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

// @author gabor
public class GameImage {

    private static final int POINTNUMBERS = DataService.getPointImagesValue();
    private static final int ALLUMBERS = DataService.getAllImagesInGame();
    private static final String IMAGE200PATH = "Pictures/Dict200x200";
    
   
    private static final String POINTIMAGESPATH = "Pictures/imagesPoint";
    private static final String[] GAMEFILE200 = new File(IMAGE200PATH).list();
    
    private static final String[] POINTFILE = new File(POINTIMAGESPATH).list();
    private static final List<JLabel> GAME_IMAGE_LIST200;
    private static final List<JLabel> GAME_IMAGE_LIST;
    private static final List<JLabel> POINT_IMAGE_LIST;
    private static final ImageIcon[] GAME_IMAGE;
    private static final ImageIcon[] GAME_IMAGE200;

    private static final int SMALL_IMAGE_WIDTH = 70;
    private static final int SMALL_IMAGE_HEIGHT = 70;

    static {
        checkImage();

        GAME_IMAGE = new ImageIcon[ALLUMBERS];

        GAME_IMAGE200 = new ImageIcon[ALLUMBERS];
        GAME_IMAGE_LIST = new ArrayList<>();
        GAME_IMAGE_LIST200 = new ArrayList<>();
        POINT_IMAGE_LIST = new ArrayList<>();
        gameImageElements();

        addImageToGAmeList();

        addImageToPointList();

    }

    private static void checkImage() {
        if (GAMEFILE200.length != ALLUMBERS) {
            JOptionPane.showMessageDialog(null, "game images count error!!");
            System.exit(0);
        }

        if (POINTFILE.length < POINTNUMBERS) {
            JOptionPane.showMessageDialog(null, "point images count error");
            System.exit(0);
        }

    }

    private GameImage() {

    }

    private static void gameImageElements() {
        for (int i = 0; i < ALLUMBERS; i++) {
            GAME_IMAGE200[i] = new ImageIcon(IMAGE200PATH + "/" + GAMEFILE200[i]);

            GAME_IMAGE[i] = new ImageIcon(GAME_IMAGE200[i].getImage().getScaledInstance(SMALL_IMAGE_WIDTH, SMALL_IMAGE_HEIGHT, Image.SCALE_DEFAULT));
        }
    }

    private static void addImageToGAmeList() {

        for (int i = 0; i < ALLUMBERS; i++) {
            GAME_IMAGE_LIST.add(new JLabel(GAME_IMAGE[i]));
            GAME_IMAGE_LIST.get(i).setName(Integer.toString(i));
            GAME_IMAGE_LIST200.add(new JLabel(GAME_IMAGE200[i]));
        }
    }


    private static void addImageToPointList() {

        Arrays.sort(POINTFILE);
        

        ImageIcon[] imageList = new ImageIcon[POINTFILE.length];
        ImageIcon[] imageListTemp = new ImageIcon[POINTFILE.length];

        for (int i = 0; i < imageList.length; i++) {
            imageListTemp[i] = new ImageIcon(POINTIMAGESPATH + "/" + POINTFILE[i]);
            imageList[i] = new ImageIcon(imageListTemp[i].getImage().getScaledInstance(SMALL_IMAGE_WIDTH, SMALL_IMAGE_HEIGHT, Image.SCALE_DEFAULT));

        }
        for (int i = 0; i < imageList.length; i++) {
            POINT_IMAGE_LIST.add(new JLabel(imageList[i]));

        }

//     
    }

    public static List<JLabel> getGameImageLIst() {
        return GAME_IMAGE_LIST;
    }

    public static List<JLabel> getPointImageList() {

        return POINT_IMAGE_LIST;
    }

    public static List<JLabel> getGameImageList200x200() {
        return GAME_IMAGE_LIST200;
    }

}
