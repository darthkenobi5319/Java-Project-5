package project5;



/**
 * @author Zhenghan Zhang
 */

public class YearNames extends AVLTree<Name> {
	public int year;
	
	/**
	 * YearNames that accept a int year and output an AVLTree
	 * @param year int value of a certain year
	 * @throws IllegalArgumentException if year if out of range of 1900-2018
	 */
	public YearNames (int year)  throws IllegalArgumentException {
		super();
		//validate the range of the year
		if (year < 1900 || year > 2018 )
			throw new IllegalArgumentException("Invalid value for year. "
					+ "Valid range is 1900-2018.");
		
		this.year = year;		
		}
	

	public void add(Name name) {
		super.add(name);
	}
	/**
	 * get the year
	 * @return the year of the YearNames
	 */
	public int getYear(){
		return this.year;
	}
	
	/**
	 * get the count of the babies with that name that year
	 * @param name a String of name
	 * @return the int value of the number of occurrences of that name
	 */
	public int getCountByName(String name) {
		return realGetCountByName(name, root);
	}
	/**
	 * The real method using recursion. This is used to get count by name
	 * @param key the name
	 * @param node the travelled node
	 * @return the total count of names
	 */
	private int realGetCountByName(String key, Node<Name> node) {
		if (node == null) {
			return 0;
		}
		if (key.equalsIgnoreCase(node.data.getName())) {
			return node.data.getCount() + realGetCountByName(key, node.left) + realGetCountByName(key, node.right);
		}
		else if (key.compareToIgnoreCase(node.data.getName())<0) {
			return realGetCountByName(key, node.left);
		}
		else {
			return realGetCountByName(key, node.right);
		}
	}
	
	/**
	 * get the fraction of the babies of the name divided by the total of babies that year
	 * @param name a String of name
	 * @return a double value of the fraction
	 */
	public double getFractionByName(String name) {
		int m = this.getCountByName(name);
		int i = this.getCount(root);
		if (m == 0 && i ==0) {
			return (double) 0;
		}
		return ((double) m/i);
	}
	
	private int getCount(Node<Name> node) {
		if (node == null) {
			return 0;
		}
		return node.data.getCount() + getCount(node.left) + getCount(node.right);
	}
	
	/**
	 * get the count of the babies with that name that year
	 * @param name a String of name
	 * @param county the name of the county
	 * @return the int value of the number of occurrences of that name
	 */
	public int getCountByNameCounty( String name, String county ) {
		if (county.equalsIgnoreCase("ALL")) {
			return getCountByName(name);
		}
		return realGetCountByNameCounty(name, county, root);
	}
	/**
	 * The real recursive method used to get name count by county
	 * @param name the name entered
	 * @param county the county entered
	 * @param node the travelled node
	 * @return the count by county
	 */
	private int realGetCountByNameCounty(String name, String county, Node<Name> node){
		if (node == null) {
			return 0;
		}
		if (name.equalsIgnoreCase(node.data.getName())) {
			if(county.equalsIgnoreCase(node.data.getCounty())) {
			return node.data.getCount() + realGetCountByNameCounty(name,county, node.left) + realGetCountByNameCounty(name,county, node.right);
			}
			else if (county.compareToIgnoreCase(node.data.getCounty())<0){
				return realGetCountByNameCounty(name,county,node.left);
			}
			else {
				return realGetCountByNameCounty(name,county,node.right);
			}
		}
		
		else if (name.compareToIgnoreCase(node.data.getName())<0) {
			return realGetCountByNameCounty(name,county, node.left);
		}
		else {
			return realGetCountByNameCounty(name,county, node.right);
		}
	}
	
	/**
	 * get the fraction of the babies of the name divided by the total of babies that year
	 * @param name a String of name
	 * @param county the name of the county
	 * @return a double value of the fraction
	 */
	public double getFractionByNameCounty(String name, String county) {
		if (county.equalsIgnoreCase("ALL")) {
			return getFractionByName(name);
		}
		int m = this.getCountByNameCounty(name, county);
		int i = getCountyCount(county,root);
		if (m == 0 && i ==0) {
			return (double) 0;
		}
		return ((double) m/i);
	}
	/**
	 * The real recursive method that counts the occurrence of a certain county
	 * @param county the county
	 * @param node the travelled node
	 * @return the number of counties
	 */
	private int getCountyCount(String county, Node<Name> node) {
		if (node == null) {
			return 0; 
		}
		if (county.equalsIgnoreCase(node.data.getCounty())) {
			return node.data.getCount() + getCountyCount(county,node.left) + getCountyCount(county,node.right);
		}
		return getCountyCount(county,node.left) + getCountyCount(county,node.right);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof YearNames))
			return false;
		YearNames other = (YearNames) obj;
		if (this.year == other.year)
			return true;
		return false;
	}
	
	public String toString () { 
		return String.format("%d",year); 
	}
}

