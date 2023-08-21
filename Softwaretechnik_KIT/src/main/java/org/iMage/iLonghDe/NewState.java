package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;
import org.iMage.iLonghDe.base.IStateMachine;

/**
 * @author Gil Baram
 *
 */
public class NewState implements IStateMachine, IState {
	
	CoffeeMachine cm;
	IState ines;

	/**
	 * NewState Constructor
	 * @param cm a given CoffeMachine
	 */
	public NewState(CoffeeMachine cm) {
		this.cm = cm;
	}

	@Override
	public void powerButtonPressed() {
		System.err.println("Wrong Button");
		throw new IllegalStateException();
		
	}

	@Override
	public void standbyButtonPressed() {
		System.err.println("Wrong Button");
		throw new IllegalStateException();
		
	}

	@Override
	public void coffeeButtonPressed() {
		display("New State Exit");
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

		return ines;
	}
	

	

}
