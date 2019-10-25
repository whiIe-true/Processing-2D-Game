package de.whiletrue.processinggame.items;

public abstract class Item {

	private String name,path;
	
	public Item(String name,String path) {
		this.name = name;
		this.path = path;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public final String getPath() {
		return this.path;
	}

	/**
	 * @param path the path to set
	 */
	public final void setPath(String path) {
		this.path = path;
	}
	
}
