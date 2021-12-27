package Part_5;

import Part_2.CompositeDataFrame;

import static Part_2.Main2.getRootCompositeDF;

public class Main5 {
    public static void main(String[] args) {
        CompositeDataFrame df_root;
        df_root = getRootCompositeDF(".txt");

        System.out.println("Visitor code:-----maximum");
        MaxVisitor vmax = new MaxVisitor("LatM");
        df_root.accept(vmax);
        System.out.println("Max:"+ vmax.getMaximum());

        System.out.println("Visitor code:-----minimum");
        MinVisitor vmin = new MinVisitor("LatM");
        df_root.accept(vmin);
        System.out.println("Min:"+ vmin.getMinimum());

        System.out.println("Visitor code:-----average");
        AverageVisitor vavg = new AverageVisitor("LatD");
        df_root.accept(vavg);
        System.out.println("Avg:"+ vavg.getAverage());

        System.out.println("Visitor code:-----sum");
        SumVisitor vsum = new SumVisitor("LatM");
        df_root.accept(vsum);
        System.out.println("Sum:"+ vsum.getSum());
    }
}
