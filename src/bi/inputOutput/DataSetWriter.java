package bi.inputOutput;

/**
 * Created by Patrick on 30.11.16.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import bi.data.DataSet;
import bi.data.Attribute;
import bi.DataMiningException;


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
            this.writer = new BufferedWriter(new FileWriter(this.filename));
            this.writeHeader();
            for (int i = 0; i < this.dataset.getLength(); i++) {
                this.writeLine(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataMiningException();
        }
    }

    public void writeHeader() {
        new AttributeWriter() {
            @Override
            public String getValueToWrite(Attribute attribute) {
                return attribute.getName();
            }
        }.write();
    }

    public void writeLine(final int pos) {
        new AttributeWriter() {
            @Override
            public String getValueToWrite(Attribute attribute) {
                return attribute.getValue(pos);
            }
        }.write();
    }

    abstract class AttributeWriter {
        public void write() {
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            for (Attribute attribute : dataset.getAttributes()) {
                if (first) {
                    first = false;
                } else {
                    builder.append(SEPERATION_CHAR);
                }
                builder.append(getValueToWrite(attribute));
            }
            try {
                writer.write(builder.toString());
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw new DataMiningException();
            }
        }

        public abstract String getValueToWrite(Attribute attribute);
    }

}
