/**
 *  ======================================================================
 *  MetaComponentSimple.java: This is a simple version of a MetaComponent.
 * 
 *  It uses jgrapht to maintain the connections between child components.
 * 
 *  Original Code: Wicked Cool Java book.
 *  Modified by: Mark Austin                                  October 2009
 *  ======================================================================
 */

package component;

import org.jgraph.*;
import org.jgraph.graph.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;

// Setup default edge

import org.jgrapht.graph.DefaultEdge;

import port.InputPort;
import port.OutputPort;

public class MetaComponentSimple<T> {

    // the graph that maintains child components

    private ListenableDirectedGraph graph;

    public MetaComponentSimple() {
        graph = new ListenableDirectedGraph<T,DefaultEdge>(DefaultEdge.class);
    }

    // ===================================================================
    // Connect an output port to an input port.
    // ===================================================================

    public void connect( OutputPort<T> out, InputPort<T> in ) {

	Component<T> source = out.getParent();
	Component<T> target =  in.getParent();

	// 1: Add parent components to graph

	if (graph.containsVertex(source) != true) {
	    graph.addVertex(source);
	}

	if (graph.containsVertex(target) != true) {
            graph.addVertex(target);
	}

	// 2: Add ports to graph

	if (graph.containsVertex(in) != true ) {
             graph.addVertex(in);
	}

	if (graph.containsVertex(out) != true) {
             graph.addVertex(out);
	}

	// 3: Add an edge from out parent to output port

	graph.addEdge(source, out);

	// 4: Add an edge from output port to input port

	graph.addEdge(out, in);

	// 5: add an edge from input port to target component

	graph.addEdge(in, target);

     }

     // ===================================================================
     // Perform the processing by processing each of the subcomponents
     // and propagating signals from outputs to inputs.
     // ===================================================================

     public void process() {
         processSubComponents();
	 propagateSignals();
     }

     // ===================================================================
     // For all connected sub componets, propagate signals from all outputs
     // to all inputs.
     // ===================================================================

     private void propagateSignals() {

        // Walk along edges and propogate output port values to
        // input port values ....

        for (Object item : graph.edgeSet()) {

	   DefaultEdge edge = (DefaultEdge) item;
	   Object source = graph.getEdgeSource( edge );
	   Object target = graph.getEdgeTarget( edge );

	   if (source instanceof OutputPort) {
	       OutputPort<T> out = (OutputPort<T>) source;
	       InputPort<T>   in = (InputPort<T>) target;
	       in.setValue( out.getValue() );
	   }
        }
     }

     // ===================================================================
     // Process all subcomponents, by calling the process methods for each.
     // ===================================================================

     private void processSubComponents() {
	for (Object item : graph.vertexSet()) {
            if (item instanceof Component) {
		((Component<T>) item).process();
	    }
	}
     }
	
     // ===================================================================
     // Returns the graph used by this MetaComponent
     // ===================================================================

     public Graph getGraph() {
        return graph;
     }
}
