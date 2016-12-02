package bi;

/**
 * Created by Patrick on 30.11.16.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import bi.model.DataSet;

public class DataSetReader {

    private static final String SEPERATION_CHAR = ",";

    private String filename;
    private DataSet dataset;

    public DataSetReader(String filename) {
        this.filename = filename;
    }

    public DataSet read() {
        BufferedReader br = null;
        dataset = new DataSet();
        boolean first = true;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
            	System.out.println("Read Line " + i);
                if (first) {
                    readHeader(line);
                    first = false;
                } else {
                    readLine(line);
                    i++;
                }
            }
            // set class as last attribute!
            dataset.setAttributeAsCl(dataset.getAttributes().size() - 1);
            dataset.setLength(i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File not found - " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("error reading file - " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("File closing reader - " + e.getMessage());
                }
            }
        }
        return dataset;
    }

    private void readHeader(String line) {
    	String split[] = line.split(SEPERATION_CHAR);
    	for (int i = 0; i < split.length; i++) {
    		dataset.addAttribute(i, split[i]);
		}
    }

    private void readLine(String line) {
    	String split[] = line.split(SEPERATION_CHAR);
    	for (int i = 0; i < split.length; i++) {
    		dataset.getAttributes().get(i).addValue(split[i]);    		
		}
    	
    }

}
