package bi;

/**
 * Created by Patrick on 30.11.16.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import bi.model.Attribute;
import bi.model.DataSet;


public class DataSetWriter {

    private static final String SEPERATION_CHAR = ",";

    private String filename;
    private BufferedWriter writer;
    private DataSet dataset;

    public DataSetWriter(String filename, DataSet dataset) {
        this.filename = filename;
        this.dataset = dataset;
    }

    public void write() {
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writeLine(true, 0);
            for (int i = 0; i < dataset.getLength(); i++) {
                writeLine(false, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error writing file - " + e.getMessage());
        }
    }
    
    public void writeLine(boolean header, int line){
    	StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Attribute attribute : dataset.getAttributes()) {
            if (first) {
                first = false;
            } else {
                builder.append(SEPERATION_CHAR);
            }
            
            if(header){
            builder.append(attribute.getName());
            } else { //line
            	builder.append(attribute.getValue(line));
            }
        }
        try {
            writer.write(builder.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error writing file - " + e.getMessage());
        }
    }

}
