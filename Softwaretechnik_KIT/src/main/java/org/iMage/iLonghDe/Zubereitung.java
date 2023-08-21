package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;
import org.iMage.iLonghDe.base.IStateMachine;

/**
 * @author Gil Baram
 *
 */
public class Zubereitung implements IStateMachine, IState {
	
	CoffeeMachine cm;
	IState izub;

	/**
	 * Zubereitung Constructor
	  * @param cm a given CoffeMachine
	 */
	public Zubereitung(CoffeeMachine cm) {
		this.cm = cm;
	}

	@Override
	public void powerButtonPressed() {
		System.err.println("Wrong Button");
		throw new IllegalStateException();
		
	}

	@Override
	public void standbyButtonPressed() {
		display("Done");
		display("");
		cm.setCurrentState2(cm.sby);
		cm.setCurrentState(cm.isby);
		
	}

	@Override
	public void coffeeButtonPressed() {

		display("Done");
		display("Waiting");
		cm.setCurrentState2(cm.wtd);
		cm.setCurrentState(cm.iwtd);
		
	}

	@Override
	public void cleaningButtonPressed() {
		System.err.println("Wrong Button");
		throw new IllegalStateException();
		
	}

	@Override
	public IState getCurrentState() {
		return izub;
	}


}
