import com.google.ortools.Loader;
import com.google.ortools.graph.MaxFlow;
import com.google.ortools.graph.MinCostFlow;

/** Sample showing how to model using the flow solver. */
public class FlowExample {
    private static void solveMinCostFlow() {
        System.out.println("Min Cost Flow Problem - Simple interface");
        final int numSources = 4;
        final int numTargets = 4;
        final int[][] costs = {
                {90, 75, 75, 80}, {35, 85, 55, 65}, {125, 95, 90, 105}, {45, 110, 95, 115}};
        final int expectedCost = 275;
        MinCostFlow minCostFlow = new MinCostFlow();
        for (int source = 0; source < numSources; ++source) {
            for (int target = 0; target < numTargets; ++target) {
                minCostFlow.addArcWithCapacityAndUnitCost(
                        source, numSources + target, 1, costs[source][target]);
            }
        }
        for (int node = 0; node < numSources; ++node) {
            minCostFlow.setNodeSupply(node, 1);
            minCostFlow.setNodeSupply(numSources + node, -1);
        }
        if (minCostFlow.solve() == MinCostFlow.Status.OPTIMAL) {
            final long totalFlowCost = minCostFlow.getOptimalCost();
            System.out.println("total flow = " + totalFlowCost + "/" + expectedCost);
            for (int i = 0; i < minCostFlow.getNumArcs(); ++i) {
                if (minCostFlow.getFlow(i) > 0) {
                    System.out.println("From source " + minCostFlow.getTail(i) + " to target "
                                       + minCostFlow.getHead(i) + ": cost " + minCostFlow.getUnitCost(i));
                }
            }
        } else {
            System.out.println("No solution found");
        }
    }

    private static void solveMaxFlow() {
        System.out.println("Max Flow Problem - Simple interface");
        final int[] tails = {0, 0, 0, 0, 1, 2, 3, 3, 4};
        final int[] heads = {1, 2, 3, 4, 3, 4, 4, 5, 5};
        final int[] capacities = {5, 8, 5, 3, 4, 5, 6, 6, 4};
        final int expectedTotalFlow = 10;
        MaxFlow maxFlow = new MaxFlow();
        for (int i = 0; i < tails.length; ++i) {
            maxFlow.addArcWithCapacity(tails[i], heads[i], capacities[i]);
        }
        if (maxFlow.solve(0, 5) == MaxFlow.Status.OPTIMAL) {
            System.out.println("Total flow " + maxFlow.getOptimalFlow() + "/" + expectedTotalFlow);
            for (int i = 0; i < maxFlow.getNumArcs(); ++i) {
                System.out.println("From source " + maxFlow.getTail(i) + " to target " + maxFlow.getHead(i)
                                   + ": " + maxFlow.getFlow(i) + " / " + maxFlow.getCapacity(i));
            }
            // TODO(user): Our SWIG configuration does not currently handle these
            // functions correctly in Java:
            // maxFlow.getSourceSideMinCut(...);
            // maxFlow.getSinkSideMinCut(...);
        } else {
            System.out.println("There was an issue with the input.");
        }
    }

    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        FlowExample.solveMinCostFlow();
        FlowExample.solveMaxFlow();
    }
}