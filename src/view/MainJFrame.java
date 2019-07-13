package view;

import data.GameImage;
import data.DataService;
import data.SoundDataService;
import logic.GetList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import logic.EndCheck;
import logic.JlabelPoint;
import logic.ListCheck;

// @author gabor
public class MainJFrame extends JFrame implements MouseListener {

    public static void main(String[] args) {
        Object []options={"  1  ", "  2  ","  3  "};
        Object message="Kérlek válassz,hogy beállíthassam a játék nehezségi szintjét! \n \n"+
                "1. Majdnem olyan okos vagyok mint Mark Zuckerberg            \n"+
                "2. Pont olyan okos vagyok mint Mark Zuckerberg    \n"+
                "3. My name is Meszaros, Lorinc Meszaros "
                ;
        int showOptionDialog = JOptionPane.showOptionDialog(null, message, "", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[0]);
        System.out.println("showOptionDialog: "+showOptionDialog);
        new MainJFrame(GameImage.getGameImageLIst(), GameImage.getPointImageList(),showOptionDialog).setVisible(true);
    }
    final Color backColor=new Color(117,110,117);

    SoundTasker soundTasker;

    List<JLabel> ImageGame;
    List<JLabel> ImagePoint;
    List<Integer> pcNumberList;
    List<Integer> playerNumberList;

    JLabel tmpLabel;
    AllImagesPanel allImagesPanel;
    JPanel gamePanel;
    JPanel pointPanel;
    HiddenImagePanel hiddenImagePanel;
    JLabel soundTrackLabel;

    InformationButton iButton;
    JButton okButton;
    //JButton musicButton;
    MusicOffOnButton moff_onButton;

    private boolean goAddImagesToPanel;
    private boolean goCheck;
    private boolean goRemove;
    boolean musicIsOn;//egy gombos zene ki-be...
    private int playMode;

    public MainJFrame(List imageList, List pointImageList,int playMode) {
        super("Crazy President");
        this.playMode=playMode;
        
        //jframe setting:
        getContentPane().setBackground(backColor);
        setIconImage(new ImageIcon("Pictures/gameicon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
       // this.setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
       this.setBounds(50, 0, 1250, 750);
     
       
        //pc feladvany letrehozasa:
        pcNumberList = GetList.doList(GetList.getMgnPC(), null,this.playMode);

        //segedek a jframe vezerleshez:
        goAddImagesToPanel = true;
        goCheck = false;
        goRemove = false;

        //images:
        tmpLabel = new JLabel();
        ImageGame = new ArrayList<>(imageList);

        ImagePoint = new ArrayList<>(pointImageList);
        //allImagesPanel:
        allImagesPanel = new AllImagesPanel(GameImage.getGameImageList200x200());
        allImagesPanel.allimage200.forEach(jl -> {
            jl.addMouseListener(this);

        });
        add(allImagesPanel);

        //gamepanel:
        gamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gamePanel.setBackground(Color.LIGHT_GRAY);
        gamePanel.setBounds(new Rectangle(450, 85, 300, 700));
        add(gamePanel);

        //pointpanel:
        pointPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pointPanel.setBackground(Color.WHITE);
        pointPanel.setBounds(new Rectangle(750, 85, 300, 700));
        add(pointPanel);

        //info:
        iButton = new InformationButton();
        add(iButton);

        //ok button:
        okButton = new JButton(new ImageIcon(DataService.getOKButtonIconPath()));
        okButton.setBackground(backColor);
        okButton.setBorder(null);
        okButton.setBounds(new Rectangle(1100, 500, 115, 65));
        add(okButton);
        okButton.addActionListener(l -> {
            if (!goCheck || gamePanel.getComponents().length == 0 || gamePanel.getComponents().length % DataService.getGameImageinRow() != 0) {
                return;
            }

            int[] chkPoint = checkPoint();
            addPointLabel(chkPoint);
            endGameCheck( EndCheck.endCheck(chkPoint, gamePanel.getComponentCount()));

        });
        //musikOffOnButton:
        moff_onButton = new MusicOffOnButton();
        add(moff_onButton);

        
        soundTrackLabel = new JLabel();
        soundTrackLabel.setForeground(Color.WHITE);
        //soundTasker:
        soundTasker = new SoundTasker();
        soundTasker.start();
        musicIsOn = true;
        //soundtrackJlabel:

        soundTrackLabel.setBounds(830, 20, 300, 30);
        add(soundTrackLabel);

        //hiddenimagesPanel:
        hiddenImagePanel = new HiddenImagePanel();
        add(hiddenImagePanel);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        addCompontents(e, gamePanel.getComponents());
        removeComponent(e, gamePanel.getComponentCount());

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        allImagesPanel.allimage200.forEach(action -> {
            if (e.getSource().equals(action)) {
                setCursor(Cursor.HAND_CURSOR);
            }
        });
//   
        Component[] gamePanelComponent = gamePanel.getComponents();
        if (gamePanelComponent.length > 0 && goRemove) {
            if (e.getSource().equals(gamePanelComponent[gamePanel.getComponentCount() - 1])) {
                setCursor(Cursor.HAND_CURSOR);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        allImagesPanel.allimage200.forEach((JLabel action) -> {
            if (e.getSource().equals(action)) {
                setCursor(Cursor.DEFAULT_CURSOR);
            }
        });
        for (Component component : gamePanel.getComponents()) {

            if (e.getSource().equals(component)) {
                setCursor(Cursor.DEFAULT_CURSOR);
            }

        }
    }

    private void updateGamePanel() {
        gamePanel.validate();
        gamePanel.repaint();
    }

    private void removeComponent(MouseEvent e, int gPanelComponentCount) {
        if (!goRemove || gPanelComponentCount < 1
                || gPanelComponentCount <= pointPanel.getComponentCount()) {
            return;
        }

        Component[] canRemove = new Component[]{gamePanel.getComponent(gPanelComponentCount - 1)};
//      
        for (Component component : canRemove) {

            if (e.getSource().equals(component)) {
                gamePanel.remove(component);
                goAddImagesToPanel = true;
                goCheck = false;
                updateGamePanel();
            }

        }
    }

    private void addCompontents(MouseEvent e, Component[] components) {
        if (components.length % DataService.getGameImageinRow() != 0 || goAddImagesToPanel) {
            for (int i = 0; i < ImageGame.size(); i++) {
                if (e.getSource().equals(allImagesPanel.allimage200.get(i))) {
                    JLabel tmpJLabel = (new JLabel(ImageGame.get(i).getIcon()));
                    tmpJLabel.setName(ImageGame.get(i).getName());
                    tmpJLabel.addMouseListener(this);
                    gamePanel.add(tmpJLabel);
                    goRemove = true;
                    if (gamePanel.getComponents().length % DataService.getGameImageinRow() == 0) {

                        goAddImagesToPanel = false;
                        goCheck = true;
                    }
                    updateGamePanel();
                }

            }
        }

    }

    private void addPointLabel(int[] listCheck) {

        List<JLabel> doJLabelList = JlabelPoint.doJLabelList(JlabelPoint.getPoint(), listCheck);

        JLabel getPoiintJlabel;
        for (int i = 0; i < doJLabelList.size(); i++) {
            getPoiintJlabel = new JLabel();
            getPoiintJlabel.setIcon(doJLabelList.get(i).getIcon());

            pointPanel.add(getPoiintJlabel);
            updatePointPanel();
        }

    }

    private void updatePointPanel() {

        pointPanel.validate();
        pointPanel.repaint();
    }

    private int[] checkPoint() {

        goAddImagesToPanel = true;
        goRemove = false;
        goCheck = false;
        StringBuilder  fourNumber = new StringBuilder("");

        for (int i = gamePanel.getComponentCount() - DataService.getGameImageinRow(); i < gamePanel.getComponentCount(); i++) {
            fourNumber.append(gamePanel.getComponent(i).getName());

        }

        playerNumberList = GetList.doList(GetList.getMgnGamer(), fourNumber.toString(),playMode);

        int[] listCheck = ListCheck.listCheck(pcNumberList, playerNumberList);

        return listCheck;

    }

    private void endGameCheck(int endCheck) {

        switch (endCheck) {
            case 1:
                addImageToHiddenPanel();
                updateHiddenPanel();

                UIManager.put("OptionPane.background", new Color(153, 219, 19));
                UIManager.put("Panel.background", new Color(153, 219, 19));
               JOptionPane.showMessageDialog(allImagesPanel,"Gratulálok");
                newGameProtokol();

                break;
            case -1:

                addImageToHiddenPanel();
                updateHiddenPanel();
                  UIManager.put("OptionPane.background", new Color(230,32,65));
                UIManager.put("Panel.background", new Color(230,32,65));
                JOptionPane.showMessageDialog(allImagesPanel, "SAJNOS NEM SIKERÜLT"
                        + System.lineSeparator()
                        + " A FEKETE MEZŐBEN A HELYES SORREND");
                newGameProtokol();
                break;

        }

    }

    private void newGameProtokol() {
        pcNumberList = GetList.doList(GetList.getMgnPC(), null,playMode);

        goAddImagesToPanel = true;
        goCheck = false;
        goRemove = false;
        gamePanel.removeAll();
        pointPanel.removeAll();
        hiddenImagePanel.removeAll();
        updateGamePanel();
        updatePointPanel();
        updateHiddenPanel();
    }

    private void addImageToHiddenPanel() {
       

        pcNumberList.forEach((imageNumber) -> {
            JLabel tmp = new JLabel(ImageGame.get(imageNumber).getIcon());
            hiddenImagePanel.add(tmp);
        });

    }

    private void updateHiddenPanel() {

        hiddenImagePanel.validate();
        hiddenImagePanel.repaint();
    }

    class MusicOffOnButton extends JButton {

        MusicOffOnButton() {
            super(new ImageIcon(DataService.getMusicOFFImagePath()));
            setBounds(new Rectangle(770, 15, 50, 50));
            addActionListener(actio -> {

                if (musicIsOn) {
                    soundTasker.stop();
                    musicIsOn = false;
                    soundTrackLabel.setText("");
                    setIcon(new ImageIcon(DataService.getMusicONImagePath()));
                } else {
                    soundTasker = new SoundTasker();
                    musicIsOn = true;
                    soundTasker.start();

                    setIcon(new ImageIcon(DataService.getMusicOFFImagePath()));

                }
            });
        }

    }

    class InformationButton extends JButton {

        public InformationButton() {

            super("HELP");
            setBounds(new Rectangle(new Point(1100, 300), new Dimension(70, 30)));
            addActionListener(actionL -> {

                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(new File(DataService.getInformationPath()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "inf file is not found!!");
                }

            });

        }

    }

   private class AllImagesPanel extends JPanel {

        List<JLabel> allimage200;

        AllImagesPanel(List<JLabel> allImagesList) {

            super(new FlowLayout());
            setBounds(new Rectangle(10, 10, 420, 630));
            allimage200 = new ArrayList<>(allImagesList);
            allimage200.forEach(jl -> {
                add(jl);
            });
        }
    }

  private  class HiddenImagePanel extends JPanel {

         HiddenImagePanel() {

            super(new FlowLayout(FlowLayout.LEFT));
            setBackground(Color.black);
            setBounds(450, 10, 300, 71);

        }

    }

   private class SoundTasker extends Thread {

        File randomSoundFile;

      

        private String getTrackName() {
            String result;
            result = randomSoundFile.toString().substring(6);
            result = result.substring(0, result.length() - 4);
            return result;
        }

        @Override
        public void run() {

                try {
                    randomSoundFile = SoundDataService.getRandomSoundFile();
                    soundTrackLabel.setText(getTrackName());

                    FileInputStream fis = new FileInputStream(randomSoundFile);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    Player player = new Player(bis);

                    player.play();
                    player.close();

                } catch (FileNotFoundException | JavaLayerException e) {
                    System.out.println(e);
                }

            
        }

    }

}
