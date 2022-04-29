package app;

import org.jacop.core.*;
import org.jacop.constraints.*;
import org.jacop.search.*;

/*
colouring the states and territories in Australia
Example 2.1.1 of Minizinc https://www.minizinc.org/doc-2.5.5/en/modelling.html
 */
public class Main {

    public static void main(String[] args) {

        // number of colours
        int NUM_COLOURS = 3;

        Store store = new Store();

        // definition of variables
        IntVar wa = new IntVar(store, "wa", 1, NUM_COLOURS);
        IntVar nt = new IntVar(store, "nt", 1, NUM_COLOURS);
        IntVar sa = new IntVar(store, "sa", 1, NUM_COLOURS);
        IntVar q = new IntVar(store, "q", 1, NUM_COLOURS);
        IntVar nsw = new IntVar(store, "nsw", 1, NUM_COLOURS);
        IntVar v = new IntVar(store, "v", 1, NUM_COLOURS);
        IntVar t = new IntVar(store, "t", 1, NUM_COLOURS);

        // definition of constraints
        store.impose(new XneqY(wa, nt));
        store.impose(new XneqY(wa, sa));
        store.impose(new XneqY(nt, sa));
        store.impose(new XneqY(nt, q));
        store.impose(new XneqY(sa, q));
        store.impose(new XneqY(sa, nsw));
        store.impose(new XneqY(sa, v));
        store.impose(new XneqY(q, nsw));
        store.impose(new XneqY(nsw, v));

        // search a feasible solution
        Search<IntVar> search = new DepthFirstSearch<IntVar>();

        // create list of variables
        IntVar[] vars = new IntVar[]{wa, nt, sa, q, nsw, v, t};

        SelectChoicePoint<IntVar> select =
                new InputOrderSelect<IntVar>(store, vars,
                        new IndomainMin<IntVar>());

        boolean result = search.labeling(store, select);

        if (result) {
            System.out.println("Solution:");
            for (IntVar var: vars){
                System.out.println(var);
            }

        } else
            System.out.println("No feasible solutions");
    }
}
