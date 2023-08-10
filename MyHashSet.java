import java.io.*;
import java.util.*;

public class MyHashSet implements HS_Interface
{	private int numBuckets; // changes over life of the hashset due to resizing the array
	private Node[] bucketArray;
	private int size; // total # keys stored in set right now

	// THIS IS A TYPICAL AVERAGE BUCKET SIZE. IF YOU GET A LOT BIGGER THEN YOU ARE MOVING AWAY FROM (1)
	private final int MAX_ACCEPTABLE_AVE_BUCKET_SIZE = 20;  // **DO NOT CHANGE THIS NUMBER**

	public MyHashSet( int numBuckets )
	{	size=0;
		this.numBuckets = numBuckets;
		bucketArray = new Node[numBuckets]; // array of linked lists
		System.out.format("IN CONSTRUCTOR: INITIAL TABLE LENGTH=%d RESIZE WILL OCCUR EVERY TIME AVE BUCKET LENGTH EXCEEDS %d\n", numBuckets, MAX_ACCEPTABLE_AVE_BUCKET_SIZE );
	}
	private int hashOf( String key, int numBuckets ) // h MUST BE IN [0..numBuckets-1]
	{

		int hash = 0;
		for (int i = 0; i < key.length(); i++) {
			hash += (hash << 5) + key.charAt(i);
		}
		return Math.abs(hash % numBuckets);
		} // U R NOT ALLOWED TO USE BUILT IN .hashCode() !!!
		//              ^^^^^^^^^^^^^^^               // U R NOT ALLOWED TO USE THE CODE BEHIND .hashCode()
		//              ILLEGAL! U MUST
		//	  REPLACE WITH YOUR ALGORITHM (loop)
		/*int hash = 0;
		for (int i = 0; i < key.length(); i++) {
			hash = (hash*19 + key.charAt(i)) % numBuckets;
		}
		if (hash < 0) {
			hash += numBuckets;
		}
		return hash; // U R NOT ALLOWED TO USE BUILT IN .hashCode() !!!
		//              ^^^^^^^^^^^^^^^               // U R NOT ALLOWED TO USE THE CODE BEHIND .hashCode()
		//              ILLEGAL! U MUST
		//	  REPLACE WITH YOUR ALGORITHM (loop)
	}*/
	public boolean add( String key )
	{
        int loc = hashOf(key, numBuckets);
        Node curr = bucketArray[loc];
        if (curr == null) {
            bucketArray[loc] = new Node(key, null);
            size++;
            if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
				upSize_ReHash_AllKeys(); 
            return true;
        }
		if (curr.data.equals(key)) {
			return false;
		}
        while (curr.next != null) {
            if (curr.next.data.equals(key)) {
                return false;
            }
            curr = curr.next;
        }
        curr.next = new Node(key,null);
        size++;
        if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
				upSize_ReHash_AllKeys(); 
        return true; 
    }
		/*if(contains(key)) {
			return false;
		}
		int loc = hashOf(key, numBuckets);
		if (bucketArray[loc] == null || bucketArray[loc].data.compareTo(key) > 0) {
			bucketArray[loc] = new Node(key, bucketArray[loc]);
		}
		else if (bucketArray[loc].next == null) {
			if(!bucketArray[loc].data.equals(key)) {
			bucketArray[loc].next = new Node(key, null);
			}
			else {
				return false;
			}
		}
		else {
			/*if (contains(key)) {
				return false;
			}
			Node curr = bucketArray[loc];
			if (curr.data.equals(key)) {
				return false;
			}
			while(curr.next != null) {
				if (curr.next.data.compareTo(key) == 0) {
					return false;
				}
				if (curr.next.data.compareTo(key) > 0) {
					break;
				}
				curr = curr.next;
			}
			if(curr.next == null) {
				curr.next = new Node(key, null);
			}
			else {
				curr.next = new Node(key, curr.next);
			}
		}
			++size; 
			if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
				upSize_ReHash_AllKeys(); 
			return true;
		}
		/*if (curr.next == null && curr.data.equals(key)) {
			return false;
		}*/
		/*Node curr = bucketArray[loc];
		//int i = 0;
		while(curr.next != null && curr.next.data.compareTo(key) < 0) {
			curr = curr.next;
			//i = i + 1;
			//System.out.println(i);
		}
		if (curr.next == null) {
			curr.next = new Node(key, null);
			size++;
			//System.out.println("excecuted");
			if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
				upSize_ReHash_AllKeys(); 
			return true;
		}
		if (!(curr.next.data.equals(key))) {
			Node addition = new Node(key, curr.next);
			curr.next = addition;
			size++;
			//System.out.println("excecuted");
			if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
				upSize_ReHash_AllKeys(); 
			return true;
		}
		System.out.println("failed");
		return false;
		/*int loc = hashOf(key, numBuckets);
		Node curr = bucketArray[loc];
		while(curr != null) {
			if(curr.data.equals(key)) {
				return false;
			}
			curr = curr.next;
		}
		if (bucketArray[loc] == null) {
			bucketArray[loc] = new Node(key, null);
		}
		else {
			Node addition = new Node(key, bucketArray[loc]);
			bucketArray[loc] = addition;
		}*/
		/*if (contains(key)) {
			return false;
		}
		int loc = hashOf(key, numBuckets);
		if (bucketArray[loc] == null) {
			bucketArray[loc] = new Node(key, null);
		}
		else {
			Node addition = new Node(key, bucketArray[loc]);
			bucketArray[loc] = addition;
		}*/
		// your code here to add the key to the table and ++ your size variable

		/*++size; // you just added a key to one of the lists
		if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
			upSize_ReHash_AllKeys(); // DOUBLE THE ARRAY .length THEN REHASH ALL KEYS
		return true;*/

	public boolean contains( String key )
	{	
		int loc = hashOf(key, numBuckets);
		Node curr = bucketArray[loc];
        while (curr != null) {
            if (curr.data.equals(key)) {
                return true;
            }
        }
		/*if(curr == null) {
			return false;
		}
		while(curr != null) {
			if (curr.data.compareTo(key) > 0) {
				return false;
			}
			if (curr.data.compareTo(key) == 0) {
				return true;
			}
			curr = curr.next;
		}*/
		return false;  // just to make it compile.
		// You hash this key. goto that list. look for this key in that list
	}

	private void upSize_ReHash_AllKeys()
	{	System.out.format("KEYS HASHED=%10d UPSIZING TABLE FROM %8d to %8d REHASHING ALL KEYS\n",
						   size, bucketArray.length, bucketArray.length*2  );
		Node[] biggerArray = new Node[ bucketArray.length * 2 ];
		this.numBuckets = biggerArray.length;
		for (int i = 0; i < bucketArray.length; i++) {
			Node curr = bucketArray[i];
			while(curr != null) {
				int loc = hashOf(curr.data, numBuckets);
                biggerArray[loc] = new Node(curr.data, biggerArray[loc]);
				curr = curr.next;
			}
		}
		bucketArray = biggerArray;
	} // END OF UPSIZE & REHASH

	public boolean remove( String key )
	{
		int loc = hashOf(key, numBuckets);
		Node curr = bucketArray[loc];
		if (curr == null) {
			return false;
		}
        if (curr.data.equals(key)) {
            bucketArray[loc] = curr.next;
			return true;
        }
        while (curr.next != null) {
            if (curr.next.data.equals(key)) {
                curr.next = curr.next.next;
                size--;
                return true;
            }
			curr = curr.next;
        }
        return false;
        /*
		if (curr.data.equals(key)) {
				bucketArray[loc] = curr.next;
				size--;
				return true;
			}
		while (curr.next != null) {
			if (curr.next.data.compareTo(key) > 0) {
				return false;
			}
			if (curr.next.data.equals(key)) {
				curr.next = curr.next.next;
				size--;
				return true;
			}
			curr = curr.next;
		}
		return false; */
	}
		
		/*if (contains(key)) {
			int loc = hashOf(key, numBuckets);
			Node curr = bucketArray[loc];
			if (curr.data.equals(key)) {
				bucketArray[loc] = curr.next;
				size--;
				return true;
			}
			while (curr.next != null) {
				if (curr.next.data.equals(key)) {
					curr.next = curr.next.next;
					size--;
					return true;
				}
				curr = curr.next;
			}
		}
		return false;
		/*int loc = hashOf(key, numBuckets);
		if (bucketArray[loc] == null) {
			return false;
		}
		if (bucketArray[loc].data.equals(key)) {
			bucketArray[loc] = bucketArray[loc].next;
			--size;
			//System.out.println("excecuted");
			return true;
		}
		else {
			Node curr = bucketArray[loc];
			while (curr.next != null && curr.next.data.compareTo(key) < 0) {
				curr = curr.next;
			}
			if(curr.next == null) {
				//System.out.println("excecuted");
				if (curr.data.equals(key)) {
					//System.out.println("excecuted");
					curr = null;
					--size;
					return true;
				}
				else {
					return false;
				}
			}
			if (curr.next.data.equals(key)) {
				//System.out.println("excecuted");
				curr.next = curr.next.next;
				--size;
				return true;
			}
		}
		return false; */

	public boolean isEmpty()
	{
		if (size <= 0) {
			return true;
		}
		return false;
	}
	public void clear()
	{
		for (int i = 0; i < bucketArray.length; i++) {
			bucketArray[i] = null;
		}
	}
	public int size()
	{
		return size;
	}	
	
} //END MyHashSet CLASS

class Node
{	String data;
	Node next;
	public Node ( String data, Node next )
	{ 	this.data = data;
		this.next = next;
	}
}



