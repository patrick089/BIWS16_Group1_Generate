package bi.generator;

/**
 * Created by Patrick on 30.11.16.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bi.data.DataSet;
import bi.data.Attribute;

public class SingleValueMissingGenerator extends MissingValueGenerator {

    public SingleValueMissingGenerator(DataSet dataset) {
        super(dataset);
    }

    private double chance;

    public DataSet doIt(double chance) {
        this.chance = chance;
        return super.doIt();
    }

    @Override
    protected Map<Attribute, List<Integer>> createMissingValuesPos() {
        int countValues = 0;
        int size = 0;
        List<Attribute> attributes = this.getMissingValueDataset()
                .getAttributes();
        // init List and count values
        Map<Attribute, List<Integer>> missingValuesPos = new HashMap<Attribute, List<Integer>>();
        for (Attribute attribute : attributes) {
            if (attribute.isCl()) {
                continue;
            }
            missingValuesPos.put(attribute, new ArrayList<Integer>());
            size += attribute.getValues().size();
        }
        long countMissingValues = Math.round(size / ((double) 100) * chance);
        // set missing values
        while (countValues < countMissingValues) {
            int posAttribute = this.rnd(attributes.size() - 1);
            // random attribute
            Attribute attribute = attributes.get(posAttribute);
            // classes are not replaced
            if (attribute.isCl()) {
                continue;
            }
            // random field
            int pos = this.rnd(attribute.getValues().size() - 1);
            // check if already found
            if (missingValuesPos.get(attribute).contains(pos)) {
                continue;
            }
            countValues++;
            missingValuesPos.get(attribute).add(pos);
        }
        return missingValuesPos;
    }

}
