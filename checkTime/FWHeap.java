import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

class FWNode
{
    int data;  // One of the input characters
    int freq;  // Frequency of the character
    FWNode left, right;
    
    FWNode(int d, int f)
    {
    	data = d;
    	freq = f;
    	left = right = null;
    }
}

class DAryHeap
{
	private int d;
	private int heapSize;
	public FWNode[] heap;

	public DAryHeap(int capacity, int numChild) 
	{
		heapSize = 0;
		d = numChild;
		heap = new FWNode[capacity + 1];
		//Arrays.fill(heap, -1);
	}

	public boolean isEmpty() 
	{
		return heapSize == 1;
	}

	public boolean isFull() 
	{
		return heapSize == heap.length;
	}
	
	// Utility function to check if this node is leaf
	boolean isLeaf(FWNode root)
	{
	    if((root.left==null) && (root.right==null))
	    	return true;
	    else
	    	return false;
	}

	public void clear() 
	{
		heapSize = 0;
	}

	private int parent(int i) 
	{
		return (i - 1) / d;
	}
	
	private int kthChild(int i, int k) 
	{
		return d * i + k;
	}

	public void insert(int d, int f) 
	{
		if (isFull())
			throw new NoSuchElementException("Overflow Exception");
		FWNode tmp = new FWNode(d,f);
		heap[heapSize++] = tmp;
		heapifyUp(heapSize - 1);
	}
	
	public void insert(FWNode n) 
	{
		if (isFull())
			throw new NoSuchElementException("Overflow Exception");
		heap[heapSize++] = n;
		heapifyUp(heapSize - 1);
	}

	public FWNode findMin() 
	{
		//if (isEmpty())
		//	throw new NoSuchElementException("Underflow Exception");
		return heap[0];
	}
	
	public FWNode delete(int ind) 
	{
		//if (isEmpty())
		//	throw new NoSuchElementException("Underflow Exception");
		FWNode keyItem = heap[ind];
		heap[ind] = heap[heapSize - 1];
		heapSize--;
		heapifyDown(ind);
		return keyItem;
	}

	private void heapifyUp(int childInd) 
	{
		FWNode tmp = heap[childInd];
		while (childInd > 0 && tmp.freq < heap[parent(childInd)].freq) 
		{
			heap[childInd] = heap[parent(childInd)];
			childInd = parent(childInd);
		}
		heap[childInd] = tmp;
	}
	
	private void heapifyDown(int ind) 
	{
		int child;
		FWNode tmp = heap[ind];
		while (kthChild(ind, 1) < heapSize) 
		{
			child = minChild(ind);
			if (heap[child].freq < tmp.freq)
				heap[ind] = heap[child];
			else
				break;
			ind = child;
		}
		heap[ind] = tmp;
	}
	
	private int minChild(int ind) 
	{
		int bestChild = kthChild(ind, 1);
		int k = 2;
		int pos = kthChild(ind, k);
		while ((k <= d) && (pos < heapSize)) {
			if (heap[pos].freq < heap[bestChild].freq)
				bestChild = pos;
			pos = kthChild(ind, k++);
		}
		return bestChild;
	}

	public void printHeap() 
	{
		System.out.print("\nHeap = ");
		for (int i = 0; i < heapSize; i++)
			System.out.println(heap[i].data + " " + heap[i].freq);
		System.out.println();
	}
	
	// The main function that builds Huffman tree
	FWNode buildHuffmanTree()
	{
		FWNode left, right, top;
	 
	    // Step 1: Create a min heap of capacity equal to size.  Initially, there are
	    // modes equal to size.
	    //MinHeap minHeap = createAndBuildMinHeap(data, freq, size);
	 
	    // Iterate while size of heap doesn't become 1
	    while (!isEmpty())
	    {
	        // Step 2: Extract the two minimum freq items from min heap
	        //left = findMin();
	        left = delete(0);
	        //System.out.println("left = "+left.data);
	        //right = findMin();
	        right = delete(0);
	        //System.out.println("right = "+right.data);
	 
	        // Step 3:  Create a new internal node with frequency equal to the
	        // sum of the two nodes frequencies. Make the two extracted node as
	        // left and right children of this new node. Add this node to the min heap
	        // '$' is a special value for internal nodes, not used
	        top = new FWNode(-1, left.freq + right.freq);
	        top.left = left;
	        top.right = right;
	        insert(top);
	    }
	 
	    // Step 4: The remaining node is the root node and the tree is complete.
	    return findMin();
	}
	
	// A utility function to print an array of size n
	void printArr(int arr[], int n)
	{
	    int i;
	    for (i = 0; i < n; ++i)
	    	System.out.print(arr[i]);
	    System.out.println("");
	}
	
	 
	// Prints huffman codes from the root of Huffman Tree.  It uses arr[] to
	// store codes
	void printCodes(FWNode root, int arr[], int top)
	{
	    // Assign 0 to left edge and recur
	    if (root.left!=null)
	    {
	        arr[top] = 0;
	        printCodes(root.left, arr, top + 1);
	    }
	 
	    // Assign 1 to right edge and recur
	    if (root.right!=null)
	    {
	        arr[top] = 1;
	        printCodes(root.right, arr, top + 1);
	    }
	 
	    // If this is a leaf node, then it contains one of the input
	    // characters, print the character and its code from arr[]
	    if (isLeaf(root))
	    {
	    	System.out.print(root.data+": ");
	        printArr(arr, top);
	    }
	}
	 
	// The main function that builds a Huffman Tree and print codes by traversing
	// the built Huffman Tree
	void HuffmanCodes()
	{
	   //  Construct Huffman Tree
		FWNode root = buildHuffmanTree();
	 
	   // Print Huffman codes using the Huffman tree built above
	   int arr[] = new int[100], top = 0;
	   //printCodes(root, arr, top);
	}
}

class FWHeap
{
	// Driver program to test above functions
	public static void main(String args[]) throws FileNotFoundException
	{
		//String s="";
		int inputarray[] = new int[1000000];
		ArrayList<Integer> arr = new ArrayList<Integer>();
		ArrayList<Integer> freq = new ArrayList<Integer>();
        String fout = "sample_encoded.bin";
        OutputStream bw = new FileOutputStream(fout);
        StringBuilder s = new StringBuilder("");
        
        try {
        	File input = new File("//Users//GrayShadow//Documents//UFL//ADS//sample2//sample_input_large.txt");
            Scanner read = new Scanner(new FileInputStream(input));

            while (read.hasNextLine()) {
                String vkey = read.nextLine();
                int index = Integer.parseInt(vkey);
                inputarray[index]++;
            }
            for(int i=0; i<inputarray.length; i++)
            {
            	if(inputarray[i] > 0)
            	{
            		arr.add(i);
            		freq.add(inputarray[i]);
            	}
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int size = arr.size();
	    DAryHeap dh = new DAryHeap(size, 4);
	    for(int i=0; i<size; i++)
	    	dh.insert(arr.get(i), freq.get(i));
	    dh.HuffmanCodes();
	}
}
