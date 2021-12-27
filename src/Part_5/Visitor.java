package Part_5;

import Part_1.*;
import Part_2.*;

public abstract class Visitor {
    private String label;
    public abstract void visit(AbstractDataFrame df);
    public void visit(CompositeDataFrame cdf){
        for(DataFrame df:cdf.getChildren()){
            df.accept(this);
        }
    }

    /**
     * Sets the label of the column to be treated.
     * @param label
     */
    public void setLabel(String label) {this.label = label;}

    /**
     * Returns the label.
     * @return The label.
     */
    public String getLabel() {return this.label;}
}
