package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;
import org.iMage.iLonghDe.base.IStateMachine;

/**
 * @author Gil Baram
 *
 */
public class Standby implements IStateMachine, IState {
	
	CoffeeMachine cm;
	IState sby;
	

	/**
	 * Standby Constructor
	  * @param cm a given CoffeMachine
	 */
	public Standby(CoffeeMachine cm) {
		this.cm = cm;

	}

	@Override
	public void powerButtonPressed() {
		
		cm.powerButtonPressed();
		
	}

	@Override
	public void standbyButtonPressed() {
		display("Waiting");
		cm.setCurrentState2(cm.wtd);
		cm.setCurrentState(cm.iwtd);
		
	}

	@Override
	public void coffeeButtonPressed() {
		System.err.println("Wrong Button");
		throw new IllegalStateException();
		
	}

	@Override
	public void cleaningButtonPressed() {
		System.err.println("Wrong Button");
		throw new IllegalStateException();
		
	}


	@Override
	public IState getCurrentState() {
		return sby;
	}





}
