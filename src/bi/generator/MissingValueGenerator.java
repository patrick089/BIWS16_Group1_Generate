package bi.generator;

/**
 * Created by Patrick on 30.11.16.
 */

import bi.data.DataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bi.data.DataSet;
import bi.data.Attribute;

public abstract class MissingValueGenerator {

    private static final String MISSING_VALUE_CHAR = "?";

    private static final int STR_LENGTH = 10;

    private DataSet dataset;
    DataSet missingValueDataset = new DataSet();

    private int pos;
    private int totalSize = 0;
    private int totalMissingValuesCount = 0;

    private Map<Attribute, List<Integer>> missingValuePos = new HashMap<Attribute, List<Integer>>();

    public MissingValueGenerator(DataSet dataset) {
        this.dataset = dataset;
    }

    public DataSet doIt() {
        this.pos = 1;
        writeLog("attribute", "#values", "#missing", "%");

        // Init Fields
        for (Attribute attribute : dataset.getAttributes()) {
            missingValueDataset.addAttribute(initAttribute(attribute));
        }

        this.missingValuePos = this.createMissingValuesPos();

        // Set Values
        for (Attribute attribute : missingValueDataset.getAttributes()) {
            if (attribute.isCl()) {
                continue;
            }
            doAttribute(attribute);
            pos++;
        }

        // Logging
        double percentage = Math.round(((double) totalMissingValuesCount)
                / ((double) totalSize) * 10000)
                / ((double) 100);
        writeLog("total", totalSize, totalMissingValuesCount, percentage);

        // Set size of the whole dataset to the amount of values in the first
        // attribute
        missingValueDataset.setLength(missingValueDataset.getAttributes()
                .get(0).getValues().size());
        return missingValueDataset;
    }

    protected abstract Map<Attribute, List<Integer>> createMissingValuesPos ();

    protected Attribute initAttribute(Attribute attribute) {
        Attribute missingValueAttribute = new Attribute(attribute.getPos(),
                attribute.getName(), attribute.isCl());

        // init missingPosList
        this.missingValuePos.put(attribute, new ArrayList<Integer>());

        // fill list
        for (String value : attribute.getValues()) {
            missingValueAttribute.addValue(value);
        }

        return missingValueAttribute;
    }

    protected void doAttribute(Attribute missingValueAttribute) {
        // set the missing values to the missing value char
        for (int pos : this.missingValuePos.get(missingValueAttribute)) {
            missingValueAttribute.getValues().set(pos, MISSING_VALUE_CHAR);
        }

        // For Output
        int size = missingValueAttribute.getValues().size();
        int missingValuesCount = countMissingValues(missingValueAttribute
                .getValues());
        double percentage = Math.round(((double) missingValuesCount)
                / ((double) size) * 10000)
                / ((double) 100);
        writeLog(pos, missingValueAttribute.getValues().size(),
                missingValuesCount, percentage);

        this.totalSize += size;
        this.totalMissingValuesCount += missingValuesCount;
    }

    protected String getValueWithMissChance(String value, int chance) {
        if ((Math.random() * 100) > chance) {
            return value;
        } else {
            return MISSING_VALUE_CHAR;
        }
    }

    protected List<Integer> randomPos(List<String> items, double chance) {
        long countMissingValues = Math.round(items.size() / ((double) 100)
                * chance);
        List<Integer> result = new ArrayList<Integer>();
        while (result.size() < countMissingValues) {
            int pos = rnd(items.size());
            if (!result.contains(pos)) {
                result.add(pos);
            }
        }
        return result;
    }

    private int countMissingValues(List<String> list) {
        int count = 0;
        for (String value : list) {
            if (value.equals(MISSING_VALUE_CHAR)) {
                count++;
            }
        }
        return count;
    }

    protected int rnd(int maxSize) {
        return (int) (Math.random() * maxSize);
    }

    private void writeLog(String attribute, String countValues,
                          String countMissingValues, String percentage) {
        System.out.println(fixedLenthString(attribute, STR_LENGTH)
                + fixedLenthString(countValues, STR_LENGTH)
                + fixedLenthString(countMissingValues, STR_LENGTH)
                + fixedLenthString(percentage, STR_LENGTH));
    }

    private void writeLog(String attribute, int countValues,
                          int countMissingValues, double percentage) {
        this.writeLog(attribute + "", countValues + "",
                countMissingValues + "", percentage + "");
    }

    private void writeLog(int attribute, int countValues,
                          int countMissingValues, double percentage) {
        this.writeLog(attribute + "", countValues + "",
                countMissingValues + "", percentage + "");
    }

    private String fixedLenthString(String string, int length) {
        return String.format("%1$" + length + "s", string);
    }

    protected DataSet getDataset () {
        return this.dataset;
    }

    protected DataSet getMissingValueDataset () {
        return this.missingValueDataset;
    }

}
