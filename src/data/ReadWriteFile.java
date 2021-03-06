package data;

import neural.TrainingSet;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadWriteFile {

    public static ArrayList<TrainingSet> readTrainingSets() {
        ArrayList<TrainingSet> trainingSets = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            char letterValue = (char) (i + 65);
            String letter = String.valueOf(letterValue);
            try {
                for (ArrayList<Integer> list : readFromFile("src\\resources\\" + letter + "learn.txt")) {
                    trainingSets.add(new TrainingSet(list, GoodOutputs.getInstance().getGoodOutput(letter)));
                }
            } catch (IOException ex) {
                Logger.getLogger(ReadWriteFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return trainingSets;
    }

    private static ArrayList<ArrayList<Integer>> readFromFile(String filename) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<Integer>> inputs = new ArrayList<>();

        try (InputStream in = new FileInputStream(new File(filename));
                Reader reader = new InputStreamReader(in, Charset.defaultCharset());
                Reader buffer = new BufferedReader(reader)) {
            int r = 0, count = 0;
            do {
                ArrayList<Integer> input = new ArrayList<>();
                for (int i = 0; i < 900; i++) {
                    if ((r = reader.read()) != -1) {
                        r -= 48;
                        input.add(r);
                    }
                }
                if (input.size() == 900) {
                    inputs.add(input);
                    count++;
                }
            } while (r != -1);// && count < 10);
        }
        return inputs;
    }

    public static void saveToFile(ArrayList<Integer> input, String filename) {
        try {
            String path = "src\\resources\\" + filename + "learn.txt";
            File file = new File(path);
            PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
            for (Integer i : input) {
                pw.write(Integer.toString(i));
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
