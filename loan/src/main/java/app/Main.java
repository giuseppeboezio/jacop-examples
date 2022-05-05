package app;

import org.jacop.core.Store;
import org.jacop.floats.constraints.PmulCeqR;
import org.jacop.floats.constraints.PmulQeqR;
import org.jacop.floats.constraints.PplusCeqR;
import org.jacop.floats.constraints.PplusQeqR;
import org.jacop.floats.core.FloatVar;
import org.jacop.floats.search.SplitSelectFloat;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.Search;

/*
taking out a short loan for one year to be repaid in 4 quarterly instalments
Example 2.1.3 of Minizinc https://www.minizinc.org/doc-2.5.5/en/modelling.html
 */
public class Main {

    public static void main(String[] args){

        Store store = new Store();

        // definition of the variables

        // quarterly repayment
        FloatVar r = new FloatVar(store,"R", 0.0, 1000.0);
        // principal initially borrowed
        FloatVar p = new FloatVar(store, "P", 0.0, 1000.0);
        // interest rate
        FloatVar i = new FloatVar(store, "I", 0.0, 100.0);

        // intermediate variables

        // balance after one quarter
        FloatVar b1 = new FloatVar(store,"B1", 0.0, 1000.0);
        // balance after two quarters
        FloatVar b2 = new FloatVar(store,"B2", 0.0, 1000.0);
        // balance after three quarters
        FloatVar b3 = new FloatVar(store,"B3", 0.0, 1000.0);
        // balance owing at the end
        FloatVar b4 = new FloatVar(store,"B4", 0.0, 1000.0);

        // imposing constraints

        FloatVar [] temp = new FloatVar[6];
        for (int j=0; j<temp.length; j++)
            temp[j] = new FloatVar(store, "temp["+j+"]", -1e150, 1e150);

        // (1.0 + I) = temp[0]
        store.impose(new PplusCeqR(i,1.0, temp[0]));
        // - R = temp[1]
        store.impose(new PmulCeqR(r, -1.0, temp[1]));
        // P * temp[0] = temp[2]
        store.impose(new PmulQeqR(p, temp[0], temp[2]));
        // B1 = temp[2] + temp[1]
        store.impose(new PplusQeqR(temp[2], temp[1], b1));
        // B1 * temp[0] = temp[3]
        store.impose(new PmulQeqR(b1, temp[0], temp[3]));
        // B2 = temp[3] + temp[1]
        store.impose(new PplusQeqR(temp[3], temp[1], b2));
        // B2 * temp[0] = temp[4]
        store.impose(new PmulQeqR(b2, temp[0], temp[4]));
        // B3 = temp[4] + temp[1]
        store.impose(new PplusQeqR(temp[4], temp[1], b3));
        // B3 * temp[0] = temp[5]
        store.impose(new PmulQeqR(b3, temp[0], temp[5]));
        // B4 = temp[5] + temp[1]
        store.impose(new PplusQeqR(temp[5], temp[1], b4));

        // searching for a feasible solution
        Search<FloatVar> search = new DepthFirstSearch<FloatVar>();
        FloatVar[] vars = new FloatVar[]{p, i, r, b4};

        SplitSelectFloat<FloatVar> select = new SplitSelectFloat<FloatVar>(store,
                vars, null);

        boolean result = search.labeling(store, select);

        if (result) {
            System.out.println("Solution:");
            for (FloatVar var: vars){
                System.out.println(var);
            }

        } else
            System.out.println("No feasible solutions");




    }
}
