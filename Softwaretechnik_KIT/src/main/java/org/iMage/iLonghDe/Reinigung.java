package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;
import org.iMage.iLonghDe.base.IStateMachine;

/**
 * @author Gil Baram
 *
 */
public class Reinigung implements IStateMachine, IState {
	
	CoffeeMachine cm;
	IState irng;

	/**
	 * Reinigung Constructor
	  * @param cm a given CoffeMachine
	 */
	public Reinigung(CoffeeMachine cm) {
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
		cm.setCurrentState(cm.isby);
		
	}

	@Override
	public void coffeeButtonPressed() {
		if (cm.isSwitchOn()) {
			display("New State Entry");
			cm.setCurrentState2(cm.nes);
			cm.setCurrentState(cm.ines);
		} else {
			System.err.println("Wrong Button");
			throw new IllegalStateException();
		}
		
	}

	@Override
	public void cleaningButtonPressed() {
		display("Waiting");
		cm.setCurrentState2(cm.wtd);
		cm.setCurrentState(cm.iwtd);
		
	}

	@Override
	public IState getCurrentState() {

		return irng;
	}
	


}
