//package entities;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StickmanSubject extends Entity {
//	
//	
//	private List<Weapon> observers = new ArrayList<Weapon>();
//	private int state;
//
//	public int getState() {
//		return state;
//	}
//
//	public void setState(int state, float delta) {
//		this.state = state;
//		notifyAllObservers(delta);
//	}
//
//	public void attach(Weapon observer){
//		observers.add(observer);		
//	}
//
//	public void notifyAllObservers(float delta){
//		for (Weapon observer : observers) {
//			observer.update(delta);
//		}
//	} 	
//
//}
