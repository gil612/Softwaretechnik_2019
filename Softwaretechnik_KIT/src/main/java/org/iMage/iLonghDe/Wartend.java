package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;
import org.iMage.iLonghDe.base.IStateMachine;

/**
 * @author Gil Baram
 *
 */
public class Wartend implements IStateMachine, IState {
	
	CoffeeMachine cm;
	IState iwtd;

	/**
	 * Wartend Constructor
	  * @param cm a given CoffeMachine
	 */
	public Wartend(CoffeeMachine cm) {
		this.cm = cm;
	}

	@Override
	public void powerButtonPressed() {
		System.err.println("Wrong Button");
		throw new IllegalStateException();
		
	}

	@Override
	public void standbyButtonPressed() {
		display("");
		cm.setCurrentState2(cm.sby);
		cm.setCurrentState(cm.isby);
		
	}

	@Override
	public void coffeeButtonPressed() {
		display("BrewCoffee");
		cm.setCurrentState2(cm.zub);
		cm.setCurrentState(cm.izub);
		
	}

	@Override
	public void cleaningButtonPressed() {
		display("Cleaning");
		cm.setCurrentState2(cm.rng);
		cm.setCurrentState(cm.irng);
		
	}

	@Override
	public IState getCurrentState() {
		
		return iwtd;
	}


}
