package logic;

import data.GameImage;
import data.DataService;
import interfaces.PointJlabelList;
import java.util.ArrayList;

import java.util.List;
import javax.swing.JLabel;

// @author gabor
public class JlabelPoint {

    private static PointJlabelList point;

    public static PointJlabelList getPoint() {
        return point;
    }

    private JlabelPoint() {
    }

    static {
        point = (int[] blackAndWhitePoints) -> {

            List<JLabel> result = new ArrayList<>();

            //
            if (blackAndWhitePoints[0] + blackAndWhitePoints[1] == 0)//no hit point
            {
                result.add(GameImage.getPointImageList().get(2)); //redx

            } else {
                for (int i = 0; i < blackAndWhitePoints[0]; i++) {
                    result.add(GameImage.getPointImageList().get(0));//black dog
                }
                for (int i = 0; i < blackAndWhitePoints[1]; i++) {
                    result.add(GameImage.getPointImageList().get(1));//white dog
                }

            }
            //upload empty spaces
            for (int i = result.size(); i < DataService.getPointnumber(); i++) {
                result.add(GameImage.getPointImageList().get(3));//Empty white
            }

            return result;
        };

    }

    public static List<JLabel> doJLabelList(PointJlabelList pjl, int[] bw) {
        return pjl.addPointJlabel(bw);
    }

}
