package bi;

/**
 * Created by Patrick on 30.11.16.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bi.model.Attribute;
import bi.model.DataSet;

public class MissingValueGenerator {

    private static final String MISSING_VALUE_CHAR = "?";

    private DataSet dataset;
    private DataSet missingValueDataset = new DataSet();

    private Map<Attribute, List<Integer>> missingValuePos = new HashMap<Attribute, List<Integer>>();

    public MissingValueGenerator(DataSet dataset) {
        this.dataset = dataset;
    }

    public DataSet generateMissingValues(int percentage) {

    	initMissingValueDataset();

    	int countValues = 0;
        int size = 0;
        List<Attribute> attributes = missingValueDataset.getAttributes();
        for (Attribute attribute : attributes) {
        	if(!attribute.isCl()){
        		size += attribute.getValues().size();
        	}
        }
        
        System.out.println("Size of Missing Value Dataset Attributes Count: " + size);
        
        long countMissingValues = Math.round(size / ((double) 100) * percentage);
        
        System.out.println(countMissingValues + " missing Values should be generated!");
        
        // set missing values
        while (countValues < countMissingValues) {
            
        	int randomAtt = rnd(attributes.size() - 1);
            // random attribute
            Attribute attribute = attributes.get(randomAtt);
            
            // classes are not replaced
            if (!attribute.isCl()) {
            	 // random field
                int randomPos = rnd(attribute.getValues().size() - 1);
                // check if already found
                if(missingValuePos.get(attribute) == null){
                	missingValuePos.put(attribute, new ArrayList<Integer>());
                }
                if (!missingValuePos.get(attribute).contains(randomPos)) {
                	//System.out.println("Missing Attribute " + countValues + " added");
                	countValues++;
                    missingValuePos.get(attribute).add(randomPos);
                }
            }
           
        }

        // Set Missing Values
        for (Attribute attribute : missingValueDataset.getAttributes()) {
            if (attribute.isCl()) {
                continue;
            }
            setMissingValueToAttribute(attribute);
        }

        // Set size of the whole dataset to the amount of values in the first
        // attribute
        missingValueDataset.setLength(missingValueDataset.getAttributes()
                .get(0).getValues().size());
        return missingValueDataset;
    }
    
    public DataSet generateMissingValues(List<Integer> percentages) {

    	initMissingValueDataset();

    	int countValues = 0;
        int size = 0;
        List<Attribute> attributes = missingValueDataset.getAttributes();
        for (Attribute attribute : attributes) {
        	if(!attribute.isCl()){
        		size += attribute.getValues().size();
        	}
        }
        
        for (Attribute attribute : missingValueDataset.getAttributes()) {
            if (attribute.isCl()) {
                continue;
            }
            missingValuePos.put(attribute, randomPos(attribute.getValues(),
                            percentages.get(attribute.getPos())));
        }

        // Set Missing Values
        for (Attribute attribute : missingValueDataset.getAttributes()) {
            if (attribute.isCl()) {
                continue;
            }
            setMissingValueToAttribute(attribute);
        }

        // Set size of the whole dataset to the amount of values in the first
        // attribute
        missingValueDataset.setLength(missingValueDataset.getAttributes()
                .get(0).getValues().size());
        return missingValueDataset;
    }

    protected void initMissingValueDataset(){
    	System.out.println("initMissingValueDataSet");
    	for (Attribute attribute : dataset.getAttributes()) {
    		
    		Attribute missingValueAttribute = new Attribute(attribute.getPos(), attribute.getName(), attribute.isCl());
    		missingValueAttribute.setValues(attribute.getValues());
            missingValueDataset.addAttribute(missingValueAttribute);
            
            if(!attribute.isCl()){
            	missingValuePos.put(attribute, new ArrayList<Integer>());
            }
        }
    }
    
    
    protected void setMissingValueToAttribute(Attribute missingValueAttribute) {
        // set the missing values to the missing value char
        for (int pos : missingValuePos.get(missingValueAttribute)) {
            missingValueAttribute.getValues().set(pos, MISSING_VALUE_CHAR);
        }

        // For Output
        int size = missingValueAttribute.getValues().size();
        int missingValuesCount = countMissingValues(missingValueAttribute.getValues());
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

    protected DataSet getDataset () {
        return this.dataset;
    }

    protected DataSet getMissingValueDataset () {
        return this.missingValueDataset;
    }

}
