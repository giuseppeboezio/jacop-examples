package app

import it.unibo.tuprolog.core.TermFormatter
import it.unibo.tuprolog.core.format
import it.unibo.tuprolog.core.parsing.parseAsStruct
import it.unibo.tuprolog.solve.SolutionFormatter
import it.unibo.tuprolog.solve.Solver
import it.unibo.tuprolog.theory.parsing.ClausesParser

fun main() {
    val parser = ClausesParser.withDefaultOperators

    val theory = parser.parseTheory("""
        dog(rottweiler).  
        cat(munchkin).  
        animal(A) :- cat(A).  
        animal(A) :- dog(A).
    """.trimIndent())

    val prologSolver = Solver.prolog.solverWithDefaultBuiltins(theory)

    prologSolver.solve("animal(A)".parseAsStruct()).forEach {
        println(
            it.format(SolutionFormatter.of(TermFormatter.prettyVariables()))
        )
    }
}