package bi.generator;

/**
 * Created by Patrick on 30.11.16.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bi.data.DataSet;
import bi.data.Attribute;

public class MultiValueMissingGenerator extends MissingValueGenerator {

    public MultiValueMissingGenerator(DataSet dataset) {
        super(dataset);
    }

    private List<Double> chances;

    public DataSet doIt(List<Double> chances) {
        this.chances = chances;
        return super.doIt();
    }

    @Override
    protected Map<Attribute, List<Integer>> createMissingValuesPos() {
        Map<Attribute, List<Integer>> missingValuesPos = new HashMap<Attribute, List<Integer>>();
        for (Attribute attribute : this.getMissingValueDataset().getAttributes()) {
            if (attribute.isCl()) {
                continue;
            }
            missingValuesPos.put(
                    attribute,
                    this.randomPos(attribute.getValues(),
                            chances.get(attribute.getPos())));
        }
        return missingValuesPos;
    }

}
