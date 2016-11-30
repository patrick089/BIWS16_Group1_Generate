package bi;

/**
 * Created by Patrick on 30.11.16.
 */

import java.util.ArrayList;
import java.util.List;
import bi.data.DataSet;
import bi.inputOutput.DataSetReader;
import bi.inputOutput.DataSetWriter;
import bi.generator.SingleValueMissingGenerator;
import bi.generator.MultiValueMissingGenerator;

public class Main {

    public static void main(String[] args) {
        DataSetReader reader = new DataSetReader(args[0]);
        DataSet dataset = reader.read();
        DataSet missingValueDataset;

        if (args.length <= 2) {
            System.out.println("Not enough parameters!");
            throw new DataMiningException();
        } else if (args.length == 3) {
            int chance = Integer.parseInt(args[2]);
            missingValueDataset = new SingleValueMissingGenerator(dataset)
                    .doIt(chance);
        } else {
            List<Double> chances = new ArrayList<Double>();
            for (int i = 2; i < args.length; i++) {
                chances.add(Double.parseDouble(args[i]));
            }
            // size -1 because class cannot be replaced
            if ((dataset.getAttributes().size() - 1) != chances.size()) {
                System.out
                        .println("Count amount differs from count attributes! ("
                                + (dataset.getAttributes().size() - 1)
                                + " expected but  "
                                + chances.size() + " given)");
                throw new DataMiningException();
            }
            missingValueDataset = new MultiValueMissingGenerator(dataset)
                    .doIt(chances);
        }

        new DataSetWriter(args[1], missingValueDataset).write();
    }

}
