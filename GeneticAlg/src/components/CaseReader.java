package components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CaseReader {
    private File file;
    private ArrayList<String> lines;

    public CaseReader(File f) {
        this.file = f;
        this.lines = null;
    }

    public CaseReader(ArrayList<String> lines) {
        this.lines = lines;
    }

    private Double[] getDouble(String[] sss) {
        Double[] dubs = new Double[sss.length];
        for (int i = 0; i < sss.length; i++) {
            if (sss[i].equalsIgnoreCase("null")) {
                dubs[i] = null;
            } else {
                try {
                    dubs[i] = Double.valueOf(sss[i]);
                } catch (NumberFormatException e) {
                    boolean bool = Boolean.parseBoolean(sss[i]);
                    if (bool) {
                        dubs[i] = 1.0;
                    }else{
                        dubs[i] = -1.0;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return dubs;
    }

    private double[] getdouble(String[] sss) {
        double[] dubs = new double[sss.length];
        for (int i = 0; i < sss.length; i++) {
            dubs[i] = Double.valueOf(sss[i]);
        }
        return dubs;
    }

    public List<TestCase> getTestCases() throws IOException {

        System.out.println("Attempting to load " + file);
        List<TestCase> cases = new ArrayList<TestCase>();
        if (lines == null) {
            lines = new ArrayList<String>();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        for (String line : lines) {
            TestCase cas = new TestCase();
            String[] splitColon = line.split(":");
            cas.out = getDouble(splitColon[1].split(","));
            cas.input = getdouble(splitColon[0].split(","));
            cases.add(cas);
        }
        return cases;
    }
}
