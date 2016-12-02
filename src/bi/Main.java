package bi;

/**
 * Created by Patrick on 30.11.16.
 */

import java.util.ArrayList;
import java.util.List;

import bi.model.DataSet;

public class Main {

    public static void main(String[] args) {
        DataSetReader reader = new DataSetReader(args[0]);
        DataSet dataset = reader.read();
        DataSet missingValueDataset;

        if (args.length < 3) {
        	System.out.println("You must enter at least 3 parameters!");
            throw new IllegalArgumentException("You must enter at least 3 parameters!");
        } else if (args.length == 3) {
            int percentage = Integer.parseInt(args[2]);
            missingValueDataset = new MissingValueGenerator(dataset).generateMissingValues(percentage);
        } else {
            
        	List<Integer> percentages = new ArrayList<Integer>();
            for (int i = 2; i < args.length; i++) {
                percentages.add(Integer.parseInt(args[i]));
            }
            
            // size -1 because class cannot be replaced
            if ((dataset.getAttributes().size() - 1) != percentages.size()) {
                System.out.println("Number of chances given is higher than number of possible attributes");
                throw new IllegalArgumentException("Number of chances given is higher than number of possible attributes");
            }
            missingValueDataset = new MissingValueGenerator(dataset).generateMissingValues(percentages);
        }

        new DataSetWriter(args[1], missingValueDataset).write();
    }

}
