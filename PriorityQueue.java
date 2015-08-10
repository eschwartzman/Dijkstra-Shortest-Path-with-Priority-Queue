import java.util.ArrayList;


class PriorityQueue<T> {
	ArrayList<HeapNode<T>> priorityQueue;
	int queueSize;


	public PriorityQueue()
	{
		priorityQueue = new ArrayList<HeapNode<T>>();
		queueSize = 0;
	}

	public boolean isEmpty()
	{
		if(queueSize == 0) {
			return true;
		}
		return false;
	}

	// Insert a pair (key, value) into the queue, and return
	// a Handle to this pair so that we can find it later in
	// constant time.
	//
	Handle insert(int key, T value)
	{
		Handle handle = new Handle(queueSize);
		HeapNode<T> newNode = new HeapNode<T>(key, value, handle);
		priorityQueue.add(newNode);
		queueSize++;

		bubbleDown(handle);
		return handle;
	}

	// Return the smallest key in the queue.
	//
	public int min()
	{
		return priorityQueue.get(0).key;
	}

	// Extract the (key, value) pair associated with the smallest
	// key in the queue and return its "value" object.
	//
	public T extractMin()
	{
		if(priorityQueue.isEmpty()){ 
			return null;
		}

		T val = priorityQueue.get(0).val;
		swap( priorityQueue.get(0), priorityQueue.get(queueSize - 1) );

		priorityQueue.get(queueSize - 1).handle.index = -1;
		priorityQueue.remove(queueSize - 1);
		queueSize--;

		heapify(0);
		return val;
	}


	// Look at the (key, value) pair referenced by Handle h.
	// If that pair is no longer in the queue, or its key
	// is <= newkey, do nothing and return false.  Otherwise,
	// replace "key" by "newkey", fixup the queue, and return
	// true.
	//
	public boolean decreaseKey(Handle h, int newkey)
	{
		if( ( h.index == -1 ) || ( priorityQueue.get(h.index).key <= newkey ) ) {
			return false;
		}
		priorityQueue.get(h.index).key = newkey;

		//fix the queue
		bubbleDown(h);
		return true;
	}


	// Get the key of the (key, value) pair associated with a 
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public int handleGetKey(Handle h)
	{
		return priorityQueue.get(h.index).key;
	}

	// Get the value object of the (key, value) pair associated with a 
	// given Handle. (This result is undefined if the handle no longer
	// refers to a pair in the queue.)
	//
	public T handleGetValue(Handle h)
	{
		if(h.index == -1){ 
			return null;
		}
		return priorityQueue.get(h.index).val;
	}

	public void swap(HeapNode<T> s1, HeapNode<T> s2){
		int temp = s1.handle.index;
		s1.handle.index = s2.handle.index;
		priorityQueue.set(s1.handle.index, s1);
		s2.handle.index = temp;
		priorityQueue.set(s2.handle.index, s2);
	}


	public int parent(int i) {
		return (int) Math.floor((i - 1)/2);
	}
	public int left(int i) {
		return (2 * i)+ 1;
	}
	public int right(int i) {
		return (2 * i)+ 2;
	}

	public void heapify(int i) {
		//make sure i is not leaf
		if(i <= (int) Math.floor(queueSize/2) ){
			int leftChild = left(i);
			int rightChild = right(i);
			int smallerChild = i;

			if (leftChild <= (queueSize-1) && priorityQueue.get(leftChild).key < priorityQueue.get(i).key){
				smallerChild = leftChild;
			}
			if (rightChild <= (queueSize-1) && priorityQueue.get(rightChild).key < priorityQueue.get(smallerChild).key){
				smallerChild = rightChild;
			}

			if (i < smallerChild){
				swap(priorityQueue.get(i), priorityQueue.get(smallerChild));
				heapify(smallerChild);
			}
		}
	}

	//While heap property is violated, bubble down by swapping to fix it
	//Used for insert(), decreaseKey()
	public void bubbleDown(Handle h){
		while( priorityQueue.get(parent(h.index)).key > priorityQueue.get(h.index).key ){
			swap(priorityQueue.get(parent(h.index)), priorityQueue.get(h.index));
		}
	}


	public String toString()
	{
		String stringToPrint = "";
		for(int i = 0; i < queueSize; i++){
			stringToPrint += "("+priorityQueue.get(i).key + ", " + priorityQueue.get(i).val.toString() + ")"+ "\n";
		}
		return stringToPrint;
	}
}
