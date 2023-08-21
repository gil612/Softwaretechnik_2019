package org.iMage.iLonghDe;

import org.iMage.iLonghDe.base.IState;
import org.iMage.iLonghDe.base.IStateMachine;

/**
 * @author Gil Baram
 *
 */
public class CoffeeMachine implements IStateMachine {
	
	
	IStateMachine sby;
	IStateMachine wtd;
	IStateMachine rng;
	IStateMachine zub;
	IStateMachine nes;
	IStateMachine iStateMachine;
	boolean switchOn;
	IState isby;
	IState iwtd;
	IState irng;
	IState izub;
	IState ines;
	
	IState istate = isby;
	
	
	/**
	 * Coffee Machine Constructor
	 * Initialize all IStateMachine and IState Variables
	 * The first state is initialized to Standby
	 */
	public CoffeeMachine() {
		sby = new Standby(this);
		isby = new Standby(this);
		wtd = new Wartend(this);
		iwtd = new Wartend(this);
		rng = new Reinigung(this);
		irng = new Reinigung(this);
		zub = new Zubereitung(this);
		izub = new Zubereitung(this);
		nes = new NewState(this);
		ines = new NewState(this);
		switchOn = false;
		iStateMachine = sby;
		display("");
		
	}

	@Override
	public void powerButtonPressed() {
		
		throw new IllegalStateException();
		
	}

	@Override
	public void standbyButtonPressed() {
		iStateMachine.standbyButtonPressed();
		
	}

	@Override
	public void coffeeButtonPressed() {
		iStateMachine.coffeeButtonPressed();
		
	}

	@Override
	public void cleaningButtonPressed() {
		iStateMachine.cleaningButtonPressed();
		
	}

	@Override
	public IState getCurrentState() {
		
		return istate;
	}
	
	/**
	 * Getter for the current stateMachine
	 * @return the current state of the Machine
	 */
	public IStateMachine getCurrentState2() {
		return iStateMachine;
	}
	
	/**
	 * Setter for testing IState
	 * @param istate a given state
	 */
	public void setCurrentState(IState istate) {
		
		this.istate = istate;
	
	}
	
	/**
	 * Setter for a state
	 * @param state a given state
	 */
	public void setCurrentState2(IStateMachine state) {
		
		this.iStateMachine = state;
	
	}
	/**
	 * @return true switchOn is true
	 */
	public boolean isSwitchOn() {
		return switchOn;
	}


	/**
	 * Setter for SwitchOn
	 * @param switchOn a given switchOn
	 */
	public void setSwitchOn(boolean switchOn) {
		this.switchOn = switchOn;
	}
	


}
