import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.ConstraintSolverParameters;
import com.google.ortools.constraintsolver.DecisionBuilder;
import com.google.ortools.constraintsolver.IntVar;
import com.google.ortools.constraintsolver.Solver;
import java.util.logging.Logger;

/** Sample showing how to model using the constraint programming solver.*/
public class RabbitsPheasants {
    private static Logger logger = Logger.getLogger(RabbitsPheasants.class.getName());

    /**
     * Solves the rabbits + pheasants problem.  We are seeing 20 heads
     * and 56 legs. How many rabbits and how many pheasants are we thus
     * seeing?
     */
    private static void solve(boolean traceSearch) {
        ConstraintSolverParameters parameters = ConstraintSolverParameters.newBuilder()
                                                                          .mergeFrom(Solver.defaultSolverParameters())
                                                                          .setTraceSearch(traceSearch)
                                                                          .build();
        Solver solver = new Solver("RabbitsPheasants", parameters);
        IntVar rabbits = solver.makeIntVar(0, 100, "rabbits");
        IntVar pheasants = solver.makeIntVar(0, 100, "pheasants");
        solver.addConstraint(solver.makeEquality(solver.makeSum(rabbits, pheasants), 20));
        solver.addConstraint(solver.makeEquality(
                solver.makeSum(solver.makeProd(rabbits, 4), solver.makeProd(pheasants, 2)), 56));
        DecisionBuilder db =
                solver.makePhase(rabbits, pheasants, Solver.CHOOSE_FIRST_UNBOUND, Solver.ASSIGN_MIN_VALUE);
        solver.newSearch(db);
        solver.nextSolution();
        logger.info(rabbits.toString());
        logger.info(pheasants.toString());
        solver.endSearch();
    }

    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        boolean traceSearch = args.length > 0 && args[1].equals("--trace");
        RabbitsPheasants.solve(traceSearch);
    }
}