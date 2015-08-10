//A HeapNode takes in an arbitrary object T, holds its key, value, and a handle that references where it is in the Queue
public class HeapNode<T> {

	int key;
	T val;
	Handle handle;

	HeapNode(int ikey, T value, Handle h){
		this.key = ikey;
		this.val = value;
		this.handle = h;
	}
}
