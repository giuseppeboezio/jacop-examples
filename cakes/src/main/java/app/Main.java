package app;

import org.jacop.constraints.LinearInt;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.*;

/*
cake baking problem
Example 2.1.2 of Minizinc https://www.minizinc.org/doc-2.5.5/en/modelling.html
 */
public class Main {

    public static void main(String[] args) {

        // definition of constants
        final int FLOUR = 4000;
        final int BANANA = 6;
        final int SUGAR = 2000;
        final int BUTTER = 500;
        final int COCOA = 500;

        final int MAX_NUM_CAKES = 100;

        // definition of the variables
        Store store = new Store();

        IntVar banana = new IntVar(store, "banana", 0, MAX_NUM_CAKES);
        IntVar chocolate = new IntVar(store, "chocolate", 0, MAX_NUM_CAKES);
        IntVar[] cakes = {banana, chocolate};


        // cost function
        IntVar cost = new IntVar(store,
                "profit",
                 - 85000, // - 400 * banana.min() - 450 * chocolate.min()
                 0 // - 400 * banana.max() - 450 * chocolate.max()
        );

        // definition of constraints
        store.impose(new LinearInt(cakes, new int[] {250, 200},"<=", FLOUR));
        store.impose(new LinearInt(new IntVar[] {banana}, new int[]{2}, "<=", BANANA));
        store.impose(new LinearInt(cakes, new int[] {75, 150}, "<=", SUGAR));
        store.impose(new LinearInt(cakes, new int[] {100, 150}, "<=", BUTTER));
        store.impose(new LinearInt(new IntVar[]{chocolate}, new int[] {75}, "<=", COCOA));

        // cost function constraint
        store.impose(new LinearInt(cakes, new int[]{-400, -450}, "==", cost));

        // search for a solution which minimizes the cost function
        IntVar[] vars = new IntVar[] {cakes[0], cakes[1], cost};

        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(vars,
                new SmallestDomain<IntVar>(),
                new IndomainMin<IntVar>());
        boolean result = label.labeling(store, select, cost);

        if (result) {
            System.out.println("Solution:");
            for (IntVar var: vars){
                System.out.println(var);
            }

        } else
            System.out.println("No feasible solutions");

    }
}
