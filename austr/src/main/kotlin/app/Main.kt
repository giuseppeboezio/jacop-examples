package app

import org.jacop.constraints.XneqY
import org.jacop.core.IntVar
import org.jacop.core.Store
import org.jacop.search.*


// number of colours
var NUM_COLOURS = 3

var store = Store()

// [[ X #> Y ]] ->  XgtY([[X]], [[Y]])
// [[ X ]] -> x;
// [[ X in c1..c2 ]] -> IntVar x = new IntVar("x", c1, c2)

// definition of variables

// [[ X #> Y ]] ->  XgtY([[X]], [[Y]])
// [[ X ]] -> x;
// [[ X in c1..c2 ]] -> IntVar x = new IntVar("x", c1, c2)

// definition of variables
val wa = IntVar(store, "wa", 1, NUM_COLOURS)
val nt = IntVar(store, "nt", 1, NUM_COLOURS)
val sa = IntVar(store, "sa", 1, NUM_COLOURS)
val q = IntVar(store, "q", 1, NUM_COLOURS)
val nsw = IntVar(store, "nsw", 1, NUM_COLOURS)
val v = IntVar(store, "v", 1, NUM_COLOURS)
val t = IntVar(store, "t", 1, NUM_COLOURS)

// definition of constraints

fun main(args: Array<String>) {
    // definition of constraints
    store.impose(XneqY(wa, nt))
    store.impose(XneqY(wa, sa))
    store.impose(XneqY(nt, sa))
    store.impose(XneqY(nt, q))
    store.impose(XneqY(sa, q))
    store.impose(XneqY(sa, nsw))
    store.impose(XneqY(sa, v))
    store.impose(XneqY(q, nsw))
    store.impose(XneqY(nsw, v))

    // search a feasible solution

    // search a feasible solution
    val search = DepthFirstSearch<IntVar>()

    // create list of variables

    // create list of variables
    val vars = arrayOf(wa, nt, sa, q, nsw, v, t)

    val select = InputOrderSelect(
        store, vars,
        IndomainMin()
    )

    val result = search.labeling(store, select)

    if (result) {
        println("Solution:")
        for (`var` in vars) {
            println(`var`)
        }
    } else {
        println("No feasible solutions")
    }
}