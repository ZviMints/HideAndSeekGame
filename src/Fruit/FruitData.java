/**
 * This Class represent Data About Fruit that implements Meta_data
 * @author Tzvi Mints and Or Abuhazira
 */
package Fruit;
public class FruitData implements Meta_data {
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	private String id;
	
	/* * * * * * * * * * * * * *  Constructor * * * * * * * * * * * * * * * */
	public FruitData(String id) { this.id = id; }

	/* * * * * * * * * * * * * * * * * * Override * * * * * * * * * * * * * * * */
	@Override
	public String toString() {
		String ans = "";
		ans += 	"ID" + ":" + getID();
		return ans;
	}

	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */ 
	public String getID() { return id; }
	public void setID(String id) { this.id = id; }
}
