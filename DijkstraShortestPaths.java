import java.util.ArrayList;
import java.util.Collections;


class ShortestPaths {
	PriorityQueue<Vertex> Q;
	Multigraph Graph;
	int numV;
	int[] distance;
	Vertex[] parent;
	Handle[] handle;
	int[] edge;
	int start;

	public ShortestPaths(Multigraph G, int startId, 
			Input input, int startTime) 
	{
		//Initialization
		start = startId;
		Graph = G;
		numV = Graph.nVertices();
		Q = new PriorityQueue<Vertex>();

		distance = new int[numV];
		handle = new Handle[numV];
		parent = new Vertex[numV];
		edge = new int[numV];

		//For every Vertex except the starting one provided, set distance = inf, parent = null
		for(int i = 0; i<numV; i++){
			Vertex u = Graph.get(i);
			if(i != startId){
				distance[u.id()] = Integer.MAX_VALUE;
				parent[u.id()] = null;
				handle[u.id()] = Q.insert(distance[u.id()], u);
			}
		}

		Vertex s = Graph.get(startId);
		distance[s.id()] = 0;	
		parent[s.id()] = null;

		handle[s.id()] = Q.insert(0, s);
		Q.decreaseKey(handle[s.id()], 0);

		while(!Q.isEmpty()){
			Vertex u = Q.extractMin();
			//Cannot reach any more Vertices from s
			if(distance[u.id()] == Integer.MAX_VALUE){
				return;
			}

			for(Vertex.EdgeIterator adj = u.adj(); adj.hasNext();){
				Edge e = adj.next();
				int weight = e.weight();

				Vertex v = e.to();

				if(Q.decreaseKey(handle[v.id()], (distance[u.id()] + weight ) )){
					//Update information
					distance[v.id()] = distance[u.id()] + weight;
					edge[v.id()] = e.id();
					parent[v.id()] = u;

				}
			}
		}
	}

	//
	// returnPath()
	// Return an array containing a list of edge ID's forming
	// a shortest path from the start vertex to the specified
	// end vertex.
	//
	public int [] returnPath(int endId) 
	{
		if(start == endId){
			int[] empty = new int[0];
			return empty;
		}else{
			Vertex end = Graph.get(endId);

			ArrayList<Integer> path = new ArrayList<Integer>();
			
			//As long as a path exists, add each edge to "path"
			while(parent[end.id()] != null){
				path.add(edge[end.id()]);
				end = parent[end.id()];
			}

			Collections.reverse(path);
			int shortestPath[] = new int[path.size()];
		
			for(int j = 0; j < path.size(); j++){
				shortestPath[j] = path.get(j);
			}
			return shortestPath;
		}
	}
}
