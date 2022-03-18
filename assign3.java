import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class assign3 {

	public static void main(String[] args) {
		String line = null;
		String parentID = null;
		int battery = 0;
		
		try(
				Scanner in = new Scanner(new File("tree.txt"));
        ){
			//creates tree object
			Tree<String> tree = new Tree<>();
			
			
			System.out.println("Reading tree stucture from 'tree.txt'.");
			
			//reads in tree from file using scanner
			//prints file contents to console
			while(in.hasNext()) {
				line = in.nextLine();
				System.out.println(line);
				String a[] = line.split(" ");
				
				//adds root
				if(parentID == null) {
					tree.addNode(a[0], null);
				}
				
				//adds contents of line, keeping track of the first index being the parent
				for(int i = 0; i < a.length; i++) {
					if(i == 0) {
						parentID = a[i];
					}
					else {
						tree.addNode(a[i], parentID);
					}
					
				}
			}//end of while loop adding tree
			
			
			System.out.println("Pre-order traversal of the tree:");
				//calls preOrder method and prints to console
				tree.preOrder(tree.root);
				System.out.println(tree.pre.toString());
			
			
			//creates console scanner to retrieve battery capacity from user
			Scanner user = new Scanner(System.in);
				//asks for battery from user and displays back to console
				System.out.println("Please input the battery capacity: ");
				battery = user.nextInt();
				System.out.println("Battery capacity is: " + battery);
				
				//close user Scanner
				user.close();
			
			//calls roomba method(modified DFS) and prints path to console
			System.out.println("Path: " + tree.roomba(battery));
			
			//calculates the total exploration of the tree based on battery and prints to console
			System.out.println("Percentage of exploration: " + tree.totalPath() + "%");
			
		}//catches if tree.txt isn't on computer
		catch(FileNotFoundException fnf){
            System.out.println("Could not find file.");
		}//catches scanner errors
		/*catch(IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}*/
	}//end of main function
	

		
	// Tree Class using nodes and arraylists	
	public static class Tree<E>{
		
		private Position<E> root = null;
		public Position<E> parent = null;
		ArrayList<E> pre = new ArrayList<E>();
		public double total = 0.0;
		public double stackSize = 1.0;
		
		//node class
		public static class Position<E>{
			public E element = null;
			//private Position<E> root = null;
			private Position<E> parent = null;
			ArrayList<Position<E>> children;
			public boolean marked = false;
			
			//node creation
			public Position(E ele, Position<E> p) {
				element = ele;
				parent = p;
				children = new ArrayList<Position<E>>();
				marked = false;
			}
			
			
		}//end of node class
		
		
		//Stack class used for roomba
		public class Stack {

			 ArrayList<Position<E>> s;
			
			//Creates instance of the stack by utilizing an ArrayList
			public Stack() {
				
				s = new ArrayList<Position<E>>();
				
			}
			
			//Adds value to top of the stack
			public void push(Position<E> k) {
				
				s.add(k);
				
			}
			
			//Returns value on top of stack and sets new top
			public Position<E> pop() {
				
				try {
					Position<E> lastE = s.get(s.size()-1);
					//System.out.println("Removed item is " + lastE);
					s.remove(s.size()-1);
					return lastE;
				}catch(IndexOutOfBoundsException e) {
					System.out.println(e);
					return null;
				}
				
			}
			
			//Returns the top without removing it from the stack
			public Position<E> peek() {
				
				try {
					Position<E> see = s.get(s.size()-1);
					return see;
				}catch(IndexOutOfBoundsException e) {
					System.out.println(e);
					return null;
				}
				
			}
			
			//Checks if stack is empty
			public boolean isEmpty() {
				
				return s.isEmpty();
				
			}
			
		}//end of stack class
		
		
		//creates empty tree
		public Tree() {}
		
		
		//adds node to tree, finds parent if not root
		public void addNode(E childID, E parID) {
			total++;
			//adds root without parent
			if(root == null) {
				Position<E> child = new Position<E>(childID, null);
				root = child;
			}
			//adds children and calls method to connect parent
			else{
				parent = findParent(root, parID);
				Position<E> child = new Position<E>(childID, null);
				child.parent = parent;
				parent.children.add(child);
			}
		}//ends addNode method
		
		
		//finds parent node to set children to correctly connect nodes 
		//uses recursion
		public Position<E> findParent(Position<E> v, E parID){
			
			//if the current node = the parent were looking for, return
			if(v.element.toString().equals(parID)) {
			return v;
			}
			
			//searches the children of the node 
			else{
				for(int j = 0; j < v.children.size(); j++) {
					Position<E> result = findParent(v.children.get(j), parID);
					
					//handles recursion return
					if(result != null) {
						return result;
					}
				}
			}
			return null;
		}//end of findParent method
		
		
		//returns element value of the tree node
		public E visit(Position<E> v){
			return v.element;
		}//end of visit method
		
		
		//preorder recursive method
		public void preOrder(Position<E> v){
			pre.add(visit(v));
			
			for(int i = 0; i < v.children.size(); i ++) {
				preOrder(v.children.get(i));
			}

		}//end of preOrder method
		
		
		//roomba function that utilizes java's stack library
		public String roomba(int battery){
			
			//keeps track of battery life
			int counter = 0;
			//create arrayList for the path and stack for dfs 
			ArrayList<E> order = new ArrayList<E>();
			Stack S = new Stack();
			Position<E> n, p;
			
			//adds root onto stack
			S.push(root);
			
			while(!(S.isEmpty())) {
				
				//pops node from stack to be evaluated by loop methods
				n = S.pop();
				
				//in case a parent with multiple children was added to the stack for printing
				if(n.marked == false){
					n.marked = true;
					
				//if the battery is half or the node doesn't has children, it returns to root
				if( (counter == (battery/2)) || (n.children.isEmpty())) {
					order.add(n.element);
					
					//goes to root via parent function
					p = n;
					while(!(p.element.toString().equals(root.element))) {
						p = p.parent;
						order.add(p.element);
					}
					//resets counter
					counter = 1;
				}
				
				//executes if battery isn't half, if the node has children, 
				//and if the node hasn't been visited
				else {
					order.add(n.element);
					
					//adds children to stack backwards so leftmost is accessed first
					for(int l = n.children.size()-1; l > -1; l--) {
						S.push(n.children.get(l));
						stackSize++;
						
						//adds parent(s) back to stack so path is printed properly
						if(!(n.element.toString().equals(root.element))) {

				 		//since the first index already has connections to the parent nodes,
				 		//it only adds the parents of the indices greater than 0
							if(l!=0) {
							S.push(n);
							
							p = n;
							//adds grand parents to stack
							while(!(p.parent.element.toString().equals(root.element))){
								
								p = p.parent;
								S.push(p);
							}
							
							}
						
						}
						
					} 
						
					//tracking battery usage
					counter++;
				}
			}//end
			//enters else statement if the node has already been "visited"
			//adds already visited parent to the path
			else {
				order.add(n.element);
				counter++;
			}
				
		}//stack is empty once here
			
			//calls method that sets the desired format for the path
			return pathString(order);
		}//end of roomba method
		
		
		//concatenates string representation of path with "-" in between nodes
		public String pathString(ArrayList<E> order) {
			String path = "";
			
			for(int m = 0; m < order.size(); m++) {
				path = path + order.get(m);
				if(m != order.size()-1) {
					path = path + "-";
				}
			}
			
			return path;
		}//end of pathString method
		
		//calculates total exploration
		public double totalPath() {
			return (double)(stackSize/total)*100;
		}//end of totalPath method
			
		
	}//end of tree class
	
	


}//end of assign3
