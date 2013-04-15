package pixlepix.complexmachines.common;

public class GrinderFuelData {

	private int ticks;
	private int watts;
	public GrinderFuelData(int ticks, int watts){
		this.setTicks(ticks);
		this.setWatts(watts);
	}
	public int getWatts() {
		return watts;
	}
	public void setWatts(int watts) {
		this.watts = watts;
	}
	public int getTicks() {
		return ticks;
	}
	public void setTicks(int ticks) {
		this.ticks = ticks;
	}
	
}
