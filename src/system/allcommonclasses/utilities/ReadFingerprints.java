package system.allcommonclasses.utilities;

import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jesse on 10/9/2014.
 */
public class ReadFingerprints {

    public static Users read2002DB1(){

        Users allUsers = new Users();

        Set<RidgeDistance> allRidgeDistances = new HashSet<RidgeDistance>();

        boolean[][][] bifurcation = new boolean[101][9][150];
        boolean[][][] disappear = new boolean[101][9][150];

        File folderMin = new File("min");
        for(File file : folderMin.listFiles()) {

            String filename = file.getName();
            String[] parts = filename.split("\\.");
            String[] betterParts = parts[0].split("_");
            int userNumber = Integer.parseInt(betterParts[0]);
            int readingNumber = Integer.parseInt(betterParts[1]);

            try {
                BufferedReader reader = new BufferedReader(new FileReader("min/" + filename));
                String line;
                reader.readLine();
                reader.readLine();
                reader.readLine();
                reader.readLine();
                while( (line = reader.readLine()) !=null){

                    String[] values = line.split(":");
                    int index1 = Integer.parseInt(values[0].trim());
                    bifurcation[userNumber][readingNumber][index1] = values[4].trim().equalsIgnoreCase("BIF");
                    disappear[userNumber][readingNumber][index1]   = values[5].trim().equalsIgnoreCase("DIS");

//                    System.out.println(values[4].trim() + ": " + bifurcation[userNumber][readingNumber][index1] + "    " +
//                            values[5].trim() + ": " + disappear[userNumber][readingNumber][index1]);


                    for(int i=7; i<values.length; i++) {
                        String currentString = values[i];
                        String[] choppedUp = currentString.split(";");
                        String[] choppedMore = choppedUp[0].split(",");

                        int ridgeCount = Integer.parseInt(choppedUp[1].trim());
                        int x2 = Integer.parseInt(choppedMore[0].trim());
                        int y2 = Integer.parseInt(choppedMore[1].trim());

                        RidgeDistance ridgeDistance = new RidgeDistance();
                        ridgeDistance.setUserNumber(userNumber);
                        ridgeDistance.setReadingNumber(readingNumber);

//                    System.out.println(index1);
                        ridgeDistance.setIndex1(index1);
//                        ridgeDistance.setIndex2(0);
                        ridgeDistance.setxValue2(x2);
                        ridgeDistance.setyValue2(y2);

                        ridgeDistance.setRidgeCount(ridgeCount);

                        allRidgeDistances.add(ridgeDistance);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }




        File folder = new File("xyt");
        for(File file : folder.listFiles()){

            String filename = file.getName();
            String[] parts = filename.split("\\.");
            String[] betterParts = parts[0].split("_");
            int userNumber = Integer.parseInt(betterParts[0]);
            int readingNumber = Integer.parseInt(betterParts[1]);
//            System.out.println(filename + " is | user number: " + userNumber + " | reading number: " + readingNumber);

            Fingerprint fingerprint = new Fingerprint();
            fingerprint.setUserNumber(userNumber);
            fingerprint.setReadingNumber(readingNumber);

            for(RidgeDistance ridge : allRidgeDistances){
                if(ridge.getUserNumber() == userNumber && ridge.getReadingNumber() == readingNumber){
                    fingerprint.getRidgeDistances().add(ridge);
                }
            }

            Set<Minutia> minutiae = fingerprint.getMinutiaePoints();

            try {
                BufferedReader reader = new BufferedReader(new FileReader("xyt/" + filename));
                String line;
                int minutiaIndex = 0;
                while( (line = reader.readLine()) !=null){
                    Minutia currentMinutia = new Minutia();
                    String[] values = line.split(" ");
                    currentMinutia.setIndex(minutiaIndex);
                    currentMinutia.setX(Long.parseLong(values[0]));
                    currentMinutia.setY(Long.parseLong(values[1]));
                    currentMinutia.setTheta(Long.parseLong(values[2]));
                    currentMinutia.setQuality(Long.parseLong(values[3]));
                    currentMinutia.setBifurcation(bifurcation[userNumber][readingNumber][minutiaIndex]);
                    currentMinutia.setDisappear(disappear[userNumber][readingNumber][minutiaIndex]);
                    minutiae.add(currentMinutia);
//                    System.out.println(userNumber + ", " + readingNumber + "  minu: " + currentMinutia.getIndex() + ", " + currentMinutia.getType());
                    minutiaIndex++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for(RidgeDistance ridge : fingerprint.getRidgeDistances()){
                for(Minutia m : fingerprint.getMinutiaePoints()){
                    if(m.getX() == ridge.getxValue2() && m.getY() == ridge.getyValue2()){
                        ridge.setIndex2(m.getIndex());
                    }
                }
            }
//            allUsers..addReading(fingerprint);
        }

        return allUsers;
    }

}
