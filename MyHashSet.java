import java.io.*;
import java.util.*;

public class MyHashSet implements HS_Interface
{	private int numBuckets; // changes over life of the hashset due to resizing the array
	private Node[] bucketArray;
	private int size; // total # keys stored in set right now

	
	private final int MAX_ACCEPTABLE_AVE_BUCKET_SIZE = 20;  

	public MyHashSet( int numBuckets )
	{	size=0;
		this.numBuckets = numBuckets;
		bucketArray = new Node[numBuckets]; // array of linked lists
		System.out.format("IN CONSTRUCTOR: INITIAL TABLE LENGTH=%d RESIZE WILL OCCUR EVERY TIME AVE BUCKET LENGTH EXCEEDS %d\n", numBuckets, MAX_ACCEPTABLE_AVE_BUCKET_SIZE );
	}
	private int hashOf( String key, int numBuckets ) 
	{
		int hash = 0;
		for (int i = 0; i < key.length(); i++) {
			hash += (hash << 5) + key.charAt(i);
		}
		return Math.abs(hash % numBuckets);
		} 
 
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

	public boolean contains( String key )
	{	
		int loc = hashOf(key, numBuckets);
		Node curr = bucketArray[loc];
        while (curr != null) {
            if (curr.data.equals(key)) {
                return true;
            }
        }
		return false;  
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
	}
		

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



