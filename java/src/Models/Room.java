package Models;

public class Room implements Comparable{
    private int id;
    private String adress;
    private int capacity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Room " + adress + ", (capacity " + capacity + ")";
    }

	@Override
	public int compareTo(Object that) {
		return this.getCapacity() - ((Room)that).getCapacity();
	}
}
