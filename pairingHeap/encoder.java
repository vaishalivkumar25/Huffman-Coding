import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

class Node
{
    int element;
    int freq;
    Node leftChild;
    Node nextSibling;
    Node prev;
    Node left;
    Node right;
 
    /* Constructor */
    public Node(int x, int f)
    {
        element = x;
        freq = f;
        leftChild = null;
        nextSibling = null;
        prev = null;
        left = right = null;
    }
}

/*class decodeTree
{
	boolean isLeaf;
	int value;
	decodeTree left;
	decodeTree right;
	
	decodeTree(int v)
	{
		value = v;
		isLeaf = false;
	}

	public decodeTree() {
		// TODO Auto-generated constructor stub
		value = -1;
		isLeaf = false;
	}
}*/

class PairHeap
{
	private int capacity;
	private Node root; 
    private Node[] treeArray;
    public String resultArray[] = new String[1000000];
    
    /* Constructor */
    public PairHeap(int capacity)
    {
        root = null;
        this.capacity = 0;
        treeArray = new Node[capacity+1];
    }
    /* Check if heap is empty */
    public boolean isEmpty() 
    {
        //return root == null;
    	if(capacity==1)
    		return true;
    	else
    		return false;
    }
    // Utility function to check if this node is leaf
 	boolean isLeaf(Node root)
 	{
 	    if((root.left==null) && (root.right==null))
 	    	return true;
 	    else
 	    	return false;
 	}
    /* Make heap logically empty */ 
    public void makeEmpty( )
    {
        root = null;
    }
    /* Function to insert data */
    public Node insert(int x, int f)
    {
    	capacity++;
        Node newNode = new Node(x,f);
        if (root == null)
            root = newNode;
        else
            root = compareAndLink(root, newNode);
        return newNode;
    }
    public Node insert(Node n)
    {
    	capacity++;
        if (root == null)
            root = n;
        else
            root = compareAndLink(root, n);
        return n;
    }
    /* Function compareAndLink */
    private Node compareAndLink(Node first, Node second)
    {
        if (second == null)
            return first;
 
        if (second.freq <= first.freq)
        {
            /* Attach first as leftmost child of second */
            second.prev = first.prev;
            first.prev = second;
            first.nextSibling = second.leftChild;
            if (first.nextSibling != null)
                first.nextSibling.prev = first;
            second.leftChild = first;
            return second;
        }
        else
        {
            /* Attach second as leftmost child of first */
            second.prev = first;
            first.nextSibling = second.nextSibling;
            if (first.nextSibling != null)
                first.nextSibling.prev = first;
            second.nextSibling = first.leftChild;
            if (second.nextSibling != null)
                second.nextSibling.prev = second;
            first.leftChild = second;
            return first;
        }
    }
    private Node combineSiblings(Node firstSibling)
    {
        if( firstSibling.nextSibling == null )
            return firstSibling;
        /* Store the subtrees in an array */
        int numSiblings = 0;
        for ( ; firstSibling != null; numSiblings++)
        {
            treeArray = doubleIfFull( treeArray, numSiblings );
            treeArray[ numSiblings ] = firstSibling;
            /* break links */
            firstSibling.prev.nextSibling = null;  
            firstSibling = firstSibling.nextSibling;
        }
        treeArray = doubleIfFull( treeArray, numSiblings );
        treeArray[ numSiblings ] = null;
        /* Combine subtrees two at a time, going left to right */
        int i = 0;
        for ( ; i + 1 < numSiblings; i += 2)
            treeArray[ i ] = compareAndLink(treeArray[i], treeArray[i + 1]);
        int j = i - 2;
        /* j has the result of last compareAndLink */
        /* If an odd number of trees, get the last one */
        if (j == numSiblings - 3)
            treeArray[ j ] = compareAndLink( treeArray[ j ], treeArray[ j + 2 ] );
        /* Now go right to left, merging last tree with */
        /* next to last. The result becomes the new last */
        for ( ; j >= 2; j -= 2)
            treeArray[j - 2] = compareAndLink(treeArray[j-2], treeArray[j]);
        return treeArray[0];
    }
    private Node[] doubleIfFull(Node [ ] array, int index)
    {
        if (index == array.length)
        {
            Node [ ] oldArray = array;
            array = new Node[index * 2];
            for( int i = 0; i < index; i++ )
                array[i] = oldArray[i];
        }
        return array;
    }
    /* Delete min element */
    public Node deleteMin( )
    {
        capacity--;
        Node x = root;
        if (root.leftChild == null)
            root = null;
        else
            root = combineSiblings( root.leftChild );
        return x;
    }
 /*   public void inorder(decodeTree r)
    {
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.value +" ");
            inorder(r.right);
        }
    }
   */ 
	// The main function that builds Huffman tree
	Node buildHuffmanTree()
	{
	    Node left, right, top;
	 
	    // Step 1: Create a min heap of capacity equal to size.  Initially, there are
	    // modes equal to size.
	 
	    // Iterate while size of heap doesn't become 1
	    while (!isEmpty())
	    {
	        // Step 2: Extract the two minimum freq items from min heap
	        left = deleteMin();
	        right = deleteMin();
	 
	        // Step 3:  Create a new internal node with frequency equal to the
	        // sum of the two nodes frequencies. Make the two extracted node as
	        // left and right children of this new node. Add this node to the min heap
	        // '$' is a special value for internal nodes, not used
	        top = new Node(-1, left.freq + right.freq);
	        top.left = left;
	        top.right = right;
	        insert(top);
	    }
	 
	    // Step 4: The remaining node is the root node and the tree is complete.
	    return root;
	}
	
	// A utility function to print an array of size n
	String printArr(int arr[], int n)
	{
		int i;
	    String res="";
	    for (i = 0; i < n; ++i)
	    {
	    	res+=arr[i];
	    }
	    return res;
	}
	
	// Prints huffman codes from the root of Huffman Tree.  It uses arr[] to
	// store codes
	void printCodes(Node root, int arr[], int top, FileWriter fw) throws IOException
	{	
	    // Assign 0 to left edge and recur
	    if (root.left!=null)
	    {
	        arr[top] = 0;
	        printCodes(root.left, arr, top + 1, fw);
	    }
	 
	    // Assign 1 to right edge and recur
	    if (root.right!=null)
	    {
	        arr[top] = 1;
	        printCodes(root.right, arr, top + 1, fw);
	    }
	 
	    // If this is a leaf node, then it contains one of the input
	    // characters, print the character and its code from arr[]
	    if (isLeaf(root))
	    {
	        resultArray[root.element] = printArr(arr, top);
	        fw.write(root.element+" "+resultArray[root.element]+"\n");
	        //System.out.println(resultArray[root.element]);
	    }
	}
	 
	// The main function that builds a Huffman Tree and print codes by traversing
	// the built Huffman Tree
	void HuffmanCodes() throws IOException
	{
		FileWriter fw = new FileWriter("code_table.txt");
	   //  Construct Huffman Tree
	   Node root = buildHuffmanTree();
	   
	   // Print Huffman codes using the Huffman tree built above
	   int arr[] = new int[100], top = 0;
	   printCodes(root, arr, top, fw);
	   fw.close();
	}
}

public class encoder {

	public static void main(String args[]) throws FileNotFoundException
	{
		int inputarray[] = new int[1000000];
		ArrayList<Integer> arr = new ArrayList<Integer>();
		ArrayList<Integer> freq = new ArrayList<Integer>();
        String fout = "encoded.bin";
        OutputStream bw = new FileOutputStream(fout);
        StringBuilder s = new StringBuilder("");
        
        try {
            File input = new File(args[0]);
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
	    PairHeap dh = new PairHeap(size);
	    for(int i=0; i<size; i++)
	    	dh.insert(arr.get(i), freq.get(i));
        
        try {	
        	dh.HuffmanCodes();
	        
	        File input = new File(args[0]);
            Scanner read = new Scanner(new FileInputStream(input));
            String bin="";

            while (read.hasNextLine()) {
                String vkey = read.nextLine();
                int index = Integer.parseInt(vkey);
                bin = dh.resultArray[index];
            	s.append(bin);
            }
            read.close();
            
            bw.write(formBinary(s.toString()));
		    bw.flush();
		    bw.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	
	static byte[] formBinary(String s)
	{
		int sLen = s.length();
	    byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
	    char c;
	    for( int i = 0; i < sLen; i++ )
	        if( (c = s.charAt(i)) == '1' )
	            toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
	        else if ( c != '0' )
	            throw new IllegalArgumentException();
	    return toReturn;
	}

}
