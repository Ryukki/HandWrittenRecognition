package neural;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import utils.MathUtils;

public class Network {

    private ArrayList<Neuron> neuronsInputs;
    private ArrayList<Neuron> neuronsInternal;
    private ArrayList<Neuron> neuronsOutputs;

    public Network() {
        neuronsInputs = new ArrayList<>();
        neuronsInternal = new ArrayList<>();
        neuronsOutputs = new ArrayList<>();

    }

    public void addNeurons(int count) {
        for (int i = 0; i < count; i++) {
            //wersja stara
            neuronsInputs.add(new Neuron());
            //wersja nowa
            neuronsInternal.add(new Neuron());
            neuronsOutputs.add(new Neuron());
        }
    }

    public void setInputs(ArrayList<Integer> inputs) {
        for (Neuron n : neuronsInputs) {
            n.setInputs(inputs);
        }
        // System.out.println("Wejscie "+inputs.toString());
//wersja nowa
        setOtherLayers();

    }

    public ArrayList<Double> getOutputs() {

        ArrayList<Double> outputsOutputs = new ArrayList<>();

        for (Neuron n : neuronsOutputs) {

            outputsOutputs.add(n.getOutput());
        }
        //System.out.println(outputsOutputs.toString());

        return outputsOutputs;
    }

    private void setOtherLayers() {

        ArrayList<Double> outputsInputs = new ArrayList<>();
        ArrayList<Double> outputsInteral = new ArrayList<>();
        //System.out.println("Ustawienie layersow");
        for (Neuron n : neuronsInputs) {
            outputsInputs.add(n.getOutput());

        }
        /// System.out.println("Inputs="+outputsInputs.toString());
        for (Neuron n : neuronsInternal) {
            n.setInputsDouble(outputsInputs);
            outputsInteral.add(n.getOutput());
        }
        // System.out.println("internal"+outputsInteral.toString());

        for (Neuron n : neuronsOutputs) {
            n.setInputsDouble(outputsInteral);

        }

        // System.out.println("Inputs="+outputsInputs.toString());
        //System.out.println("internal"+outputsInteral.toString());
    }

    public void adjustWages(ArrayList<Double> goodOutput) {

        //wersja stara
//        for (int i = 0; i < neuronsInputs.size(); i++) {
//            double delta = goodOutput.get(i) - neuronsInputs.get(i).getOutput();
//            neuronsInputs.get(i).adjustWeights(delta);
//        }
//http://edu.pjwstk.edu.pl/wyklady/nai/scb/wyklad3/w3.htm
        for (int i = 0; i < neuronsOutputs.size(); i++) {
            double delta = goodOutput.get(i) - neuronsOutputs.get(i).getOutput();
            double d = delta * MathUtils.derivative(neuronsOutputs.get(i).getSum());
            neuronsOutputs.get(i).setD(d);

        }

        for (int i = 0; i < neuronsInternal.size(); i++) {

            double sumError = 0;
            for (int j = 0; j < neuronsOutputs.size(); j++) {
                //index neuron j, index of weights from layer-1
                sumError += neuronsOutputs.get(j).getWeight(i) * neuronsOutputs.get(j).getD();
            }

            double d = sumError * MathUtils.derivative(neuronsInternal.get(i).getSum());
            neuronsInternal.get(i).setD(d);

        }
        for (int i = 0; i < neuronsInputs.size(); i++) {

            double sumError = 0;
            for (int j = 0; j < neuronsInternal.size(); j++) {
                //index neuron j, index of weights from layer-1
                sumError += neuronsInternal.get(j).getWeight(i) * neuronsInternal.get(j).getD();
            }

            double d = sumError * MathUtils.derivative(neuronsInputs.get(i).getSum());
            neuronsInputs.get(i).setD(d);

        }

        for (Neuron n : neuronsInputs) {
            n.adjustWeights(true);
        }
        for (Neuron n : neuronsInternal) {
            n.adjustWeights(false);
        }
        for (Neuron n : neuronsOutputs) {
            n.adjustWeights(false);
        }

    }

    public void saveWeightsToFile() throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Neuron n : neuronsInputs) {
            ArrayList<Double> save = n.getWeight();
            for (Double d : save) {
                stringBuilder.append(d);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        String finalString = stringBuilder.toString();
        PrintWriter saving = new PrintWriter("weightsInputs.txt");
        saving.println(finalString);
        saving.close();

        stringBuilder = new StringBuilder();
        for (Neuron n : neuronsInternal) {
            ArrayList<Double> save = n.getWeight();
            for (Double d : save) {
                stringBuilder.append(d);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        finalString = stringBuilder.toString();
        saving = new PrintWriter("weightsInternal.txt");
        saving.println(finalString);
        saving.close();

        stringBuilder = new StringBuilder();
        for (Neuron n : neuronsOutputs) {
            ArrayList<Double> save = n.getWeight();
            for (Double d : save) {
                stringBuilder.append(d);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        finalString = stringBuilder.toString();
        saving = new PrintWriter("weightsOutputs.txt");
        saving.println(finalString);
        saving.close();
    }

    public void loadWeightsFromFile() throws IOException {
        ArrayList<ArrayList<Double>> weightsIn = loadWeightsFromFile("weightsInputs.txt");
        ArrayList<ArrayList<Double>> weightsIntern = loadWeightsFromFile("weightsInternal.txt");
        ArrayList<ArrayList<Double>> weightsOut = loadWeightsFromFile("weightsOutputs.txt");

        for (int i = 0; i < neuronsInputs.size(); i++) {
            neuronsInputs.get(i).setWeights(weightsIn.get(i));
        }
        for (int i = 0; i < neuronsInternal.size(); i++) {
            neuronsInternal.get(i).setWeights(weightsIntern.get(i));
        }
        for (int i = 0; i < neuronsOutputs.size(); i++) {
            neuronsOutputs.get(i).setWeights(weightsOut.get(i));
        }
    }

    private ArrayList<ArrayList<Double>> loadWeightsFromFile(String fileName) throws IOException {
        ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
        File file = new File(fileName);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null) {
                String[] elements = text.split("[\\s+]");
                list.add(new ArrayList<Double>());
                ArrayList<Double> arrayList = list.get(list.size() - 1);
                for (String s : elements) {

                    double data = ParseDouble(s);
                    if (data != 0) {
                        arrayList.add(data);
                    }

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.print("Error");
            }
        }

        return list;

    }

    private double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (NumberFormatException e) {
                return -1;   
            }
        } else {
            return 0;
        }
    }
}
