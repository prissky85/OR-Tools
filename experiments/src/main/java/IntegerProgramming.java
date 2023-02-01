//package experiments.ortools;
//
//import com.google.ortools.Loader;
//import com.google.ortools.linearsolver.MPConstraint;
//import com.google.ortools.linearsolver.MPObjective;
//import com.google.ortools.linearsolver.MPSolver;
//import com.google.ortools.linearsolver.MPVariable;
//
///** Integer programming example that shows how to use the API. */
//public class IntegerProgramming {
//    private static void runIntegerProgrammingExample(String solverType) {
//        MPSolver solver = MPSolver.createSolver(solverType);
//        if (solver == null) {
//            System.out.println("Could not create solver " + solverType);
//            return;
//        }
//        double infinity = java.lang.Double.POSITIVE_INFINITY;
//        // x1 and x2 are integer non-negative variables.
//        MPVariable x1 = solver.makeIntVar(0.0, infinity, "x1");
//        MPVariable x2 = solver.makeIntVar(0.0, infinity, "x2");
//
//        // Minimize x1 + 2 * x2.
//        MPObjective objective = solver.objective();
//        objective.setCoefficient(x1, 1);
//        objective.setCoefficient(x2, 2);
//
//        // 2 * x2 + 3 * x1 >= 17.
//        MPConstraint ct = solver.makeConstraint(17, infinity);
//        ct.setCoefficient(x1, 3);
//        ct.setCoefficient(x2, 2);
//
//        final MPSolver.ResultStatus resultStatus = solver.solve();
//
//        // Check that the problem has an optimal solution.
//        if (resultStatus != MPSolver.ResultStatus.OPTIMAL) {
//            System.err.println("The problem does not have an optimal solution!");
//            return;
//        }
//
//        // Verify that the solution satisfies all constraints (when using solvers
//        // others than GLOP_LINEAR_PROGRAMMING, this is highly recommended!).
//        if (!solver.verifySolution(/*tolerance=*/1e-7, /* log_errors= */ true)) {
//            System.err.println("The solution returned by the solver violated the"
//                               + " problem constraints by at least 1e-7");
//            return;
//        }
//
//        System.out.println("Problem solved in " + solver.wallTime() + " milliseconds");
//
//        // The objective value of the solution.
//        System.out.println("Optimal objective value = " + solver.objective().value());
//
//        // The value of each variable in the solution.
//        System.out.println("x1 = " + x1.solutionValue());
//        System.out.println("x2 = " + x2.solutionValue());
//
//        System.out.println("Advanced usage:");
//        System.out.println("Problem solved in " + solver.nodes() + " branch-and-bound nodes");
//    }
//
//    public static void main(String[] args) throws Exception {
//        Loader.loadNativeLibraries();
//        System.out.println("---- Integer programming example with SCIP (recommended) ----");
//        runIntegerProgrammingExample("SCIP");
//        System.out.println("---- Integer programming example with CBC ----");
//        runIntegerProgrammingExample("CBC");
//        System.out.println("---- Integer programming example with CP-SAT ----");
//        runIntegerProgrammingExample("SAT");
//    }
//}