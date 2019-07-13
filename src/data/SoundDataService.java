package data;


import java.io.File;
import java.util.Random;



     public class SoundDataService {

        private static final String SOUNDFOLDER = DataService.getSoundpath();
        private static String[] fileName;
        private static int lastNumber;
        private static File actualSoundFile;

        private SoundDataService() {
        }

        static {
            fileName = new File(SOUNDFOLDER).list();
            lastNumber = -1;
          
        }

        public static File getRandomSoundFile() {
          
            int actualNumber;
   
            do {
                actualNumber = new Random().nextInt(fileName.length);
             
            } while (actualNumber == lastNumber);

            lastNumber = actualNumber;
            actualSoundFile=new File(SOUNDFOLDER + "/" + fileName[actualNumber]);
            return actualSoundFile;
        }

        public static File getActualSoundFile() {
            return actualSoundFile;
        }
        
    }
