/**
 * This Class represent Data About Fruit that implements Meta_data
 * @author Tzvi Mints and Or Abuhazira
 */
package Fruit;
public class FruitData implements Meta_data {
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	private String id;
	private boolean Dead; // If Fruit is Dead

	/* * * * * * * * * * * * * *  Constructor * * * * * * * * * * * * * * * */
	public FruitData(String id) { this.id = id; this.Dead = false;}

	/* * * * * * * * * * * * * * * * * * Override * * * * * * * * * * * * * * * */
	@Override
	public String toString() {
		String ans = "";
		ans += 	"ID" + ":" + getID();
		return ans;
	}

	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */ 
	public String getID() { return id; } // Get ID
	public void setID(String id) { this.id = id; } // Set ID
	public void Eaten() {this.Dead = true; } // Make Fruit Dead
	public boolean Dead() { return this.Dead; } // Check if Fruit is Dead
	public void MakeAlive() {this.Dead = false;} // Make Fruit Alive
}
