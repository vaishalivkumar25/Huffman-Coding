import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MinHeapNode
{
    int data;  // One of the input characters
    int freq;  // Frequency of the character
    MinHeapNode left, right; // Left and right child of this node
}

//A Min Heap:  Collection of min heap (or Hufmman tree) nodes
class MinHeap
{
	int size;    // Current size of min heap
	int capacity;   // capacity of min heap
	MinHeapNode[] array;  // Array of minheap node pointers
}

class BHeap
{
	// A utility function allocate a new min heap node with given character
	// and frequency of the character
	static public MinHeapNode newNode(int data, int freq)
	{
	    MinHeapNode temp = new MinHeapNode();
	    temp.left = temp.right = null;
	    temp.data = data;
	    temp.freq = freq;
	    return temp;
	}
	
	// A utility function to create a min heap of given capacity
	static public MinHeap createMinHeap(int capacity)
	{
	    MinHeap minHeap = new MinHeap();
	    minHeap.size = 0;  // current size is 0
	    minHeap.capacity = capacity;
	    minHeap.array = new MinHeapNode[capacity];
	    return minHeap;
	}
	
	static public MinHeap swapMinHeapNode(MinHeap minHeap, int smallest, int idx)
	{
		MinHeapNode t = null;
		t = minHeap.array[smallest];
		minHeap.array[smallest] = minHeap.array[idx];
		minHeap.array[idx] = t;
		
		return minHeap;
	}
	
	// The standard minHeapify function.
	static MinHeap minHeapify(MinHeap minHeap, int idx)
	{
	    int smallest = idx;
	    int left = 2 * idx + 1;
	    int right = 2 * idx + 2;
	 
	    if (left < minHeap.size &&
	        minHeap.array[left].freq < minHeap.array[smallest].freq)
	      smallest = left;
	 
	    if (right < minHeap.size &&
	        minHeap.array[right].freq < minHeap.array[smallest].freq)
	      smallest = right;
	 
	    if (smallest != idx)
	    {
	        //swapMinHeapNode(minHeap.array[smallest], minHeap.array[idx]);
	    	minHeap = swapMinHeapNode(minHeap, smallest, idx);
	        minHeap = minHeapify(minHeap, smallest);
	    }
	    return minHeap;
	}
	
	// A utility function to check if size of heap is 1 or not
	static boolean isSizeOne(MinHeap minHeap)
	{
	    if(minHeap.size == 1)
	    	return true;
	    else
	    	return false;
	}
	
	// A standard function to extract minimum value node from heap
	static MinHeapNode extractMin(MinHeap minHeap)
	{
	    MinHeapNode temp = minHeap.array[0];
	    minHeap.array[0] = minHeap.array[minHeap.size - 1];
	    minHeap.size = minHeap.size-1;
	    minHeap = minHeapify(minHeap, 0);
	    return temp;
	}
	
	// A utility function to insert a new node to Min Heap
	static MinHeap insertMinHeap(MinHeap minHeap, MinHeapNode minHeapNode)
	{
	    ++minHeap.size;
	    int i = minHeap.size - 1;
	    while (i!=0 && minHeapNode.freq < minHeap.array[(i - 1)/2].freq)
	    {
	        minHeap.array[i] = minHeap.array[(i - 1)/2];
	        i = (i - 1)/2;
	    }
	    minHeap.array[i] = minHeapNode;
	    
	    return minHeap;
	}
	
	// A standard function to build min heap
	static MinHeap buildMinHeap(MinHeap minHeap)
	{
	    int n = minHeap.size - 1;
	    int i;
	    for (i = (n - 1) / 2; i >= 0; --i)
	        minHeap = minHeapify(minHeap, i);
	    return minHeap;
	}
	
	// A utility function to print an array of size n
	static void printArr(int arr[], int n)
	{
	    int i;
	    for (i = 0; i < n; ++i)
	    	System.out.print(arr[i]);
	    System.out.println("");
	}
	
	// Utility function to check if this node is leaf
	static boolean isLeaf(MinHeapNode root)
	{
	    if((root.left==null) && (root.right==null))
	    	return true;
	    else
	    	return false;
	}
	 
	// Creates a min heap of capacity equal to size and inserts all character of 
	// data[] in min heap. Initially size of min heap is equal to capacity
	static MinHeap createAndBuildMinHeap(int data[], int freq[], int size)
	{
	    MinHeap minHeap = createMinHeap(size);
	    for (int i = 0; i < size; ++i)
	        minHeap.array[i] = newNode(data[i], freq[i]);
	    minHeap.size = size;
	    minHeap = buildMinHeap(minHeap);
	    return minHeap;
	}
	
	// The main function that builds Huffman tree
	static MinHeapNode buildHuffmanTree(int data[], int freq[], int size)
	{
	    MinHeapNode left, right, top;
	 
	    // Step 1: Create a min heap of capacity equal to size.  Initially, there are
	    // modes equal to size.
	    MinHeap minHeap = createAndBuildMinHeap(data, freq, size);
	 
	    // Iterate while size of heap doesn't become 1
	    while (!isSizeOne(minHeap))
	    {
	        // Step 2: Extract the two minimum freq items from min heap
	        left = extractMin(minHeap);
	        right = extractMin(minHeap);
	 
	        // Step 3:  Create a new internal node with frequency equal to the
	        // sum of the two nodes frequencies. Make the two extracted node as
	        // left and right children of this new node. Add this node to the min heap
	        // '$' is a special value for internal nodes, not used
	        top = newNode('$', left.freq + right.freq);
	        top.left = left;
	        top.right = right;
	        insertMinHeap(minHeap, top);
	    }
	 
	    // Step 4: The remaining node is the root node and the tree is complete.
	    return extractMin(minHeap);
	}
	 
	// Prints huffman codes from the root of Huffman Tree.  It uses arr[] to
	// store codes
	static void printCodes(MinHeapNode root, int arr[], int top)
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
	static void HuffmanCodes(int data[], int freq[], int size)
	{
	   //  Construct Huffman Tree
	   MinHeapNode root = buildHuffmanTree(data, freq, size);
	 
	   // Print Huffman codes using the Huffman tree built above
	   int arr[] = new int[100], top = 0;
	   //printCodes(root, arr, top);
	}
	
	static int[] toIntArray(List<Integer> list)
	{
		  int[] ret = new int[list.size()];
		  for(int i = 0;i < ret.length;i++)
		    ret[i] = list.get(i);
		  return ret;
	}
	
	// Driver program to test above functions
	public static void main(String args[]) throws FileNotFoundException
	{
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
        MinHeap minHeap = createMinHeap(size);
	    for(int i=0; i<size; i++)
	    {
	    	MinHeapNode minHeapNode = newNode(arr.get(i), freq.get(i));
	    	minHeap = insertMinHeap(minHeap, minHeapNode);
	    }
	    int data[] = toIntArray(arr);
	    int f[] = toIntArray(freq);
	    HuffmanCodes(data, f, size);     
   	}
}
