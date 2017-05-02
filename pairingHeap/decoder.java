import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

class decodeTree
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
}

public class decoder {
	
	static String encodedFilePath = "";
	static String codeTablePath = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		encodedFilePath = args[0];
		codeTablePath = args[1];
		
		decodeTree root = constructDecodeTree();
        //dh.inorder(root);
        
        printElements(root);

	}
	
	static decodeTree constructDecodeTree()
	{
		decodeTree root = new decodeTree(), tempRoot = root;
		try
		{
			File input = new File(codeTablePath);
	        Scanner read = new Scanner(new FileInputStream(input));
	        
	        while (read.hasNextLine()) {
                String[] line = read.nextLine().split(" ");
                int num = Integer.parseInt(line[0]);
                
                for(int i=0; i<line[1].length(); i++)
                {
                	int digit = line[1].charAt(i) - '0';
                	decodeTree node = new decodeTree();
            		if(digit==0)
            		{
            			if(root.left == null)
            				root.left = node;
            			root = root.left;
            		}
            		else
            		{
            			if(root.right == null)
            				root.right = node;
            			root = root.right;
            		}
                }
                root.isLeaf = true;
                root.value = num;
                root = tempRoot;
            }
	        read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		return root;
	}
	
	static String toBinaryString(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
	    for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
	        sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
	    return sb.toString();
	}
	
	static void printElements(decodeTree root)
	{
		decodeTree tempRoot = root;
		try
		{
			Path fileLocation = Paths.get(encodedFilePath);
		    byte[] inputStream = Files.readAllBytes(fileLocation);
		    String binary = toBinaryString(inputStream);
		    FileWriter fw = new FileWriter("decoded.txt");
			
		    for(int i=0; i<binary.length(); i++)
		    {
		    	if(binary.charAt(i) == '0')
		    		root = root.left;
		    	else
		    		root = root.right;
		    	
		    	if(root.isLeaf == true)
		    	{
		    		fw.write(root.value+"\n");
		    		root = tempRoot;
		    	}
		    }
		    fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
}
