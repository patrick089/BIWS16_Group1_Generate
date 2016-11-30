package bi.inputOutput;

/**
 * Created by Patrick on 30.11.16.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import bi.data.DataSet;
import bi.DataMiningException;

public class DataSetReader {

    private static final String SEPERATION_CHAR = ",";

    private String filename;
    private DataSet dataset;

    public DataSetReader(String filename) {
        this.filename = filename;
    }

    public DataSet read() {
        BufferedReader br = null;
        this.dataset = new DataSet();
        boolean first = true;
        try {
            br = new BufferedReader(new FileReader(this.filename));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (first) {
                    this.readHeader(line);
                    first = false;
                } else {
                    this.readLine(line);
                    i++;
                }
            }
            // Hack: The last attribute is the cl (hardcoded)
            this.dataset.setAttributeAsCl(this.dataset.getAttributes()
                    .size() - 1);
            this.dataset.setLength(i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new DataMiningException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataMiningException();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new DataMiningException();
                }
            }
        }
        return dataset;
    }

    private void readHeader(String line) {
        int i = 0;
        StringTokenizer st = new StringTokenizer(line, SEPERATION_CHAR);
        while (st.hasMoreTokens()) {
            this.dataset.addAttribute(i++, st.nextToken());
        }
    }

    private void readLine(String line) {
        int i = 0;
        StringTokenizer st = new StringTokenizer(line, SEPERATION_CHAR);
        while (st.hasMoreTokens()) {
            this.dataset.getAttributes().get(i++).addValue(st.nextToken());
        }
    }

}
