package org.iMage.iLonghDe;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

/**
 * @author Gil Baram
 *
 */
@SuppressWarnings("deprecation")
public class CoffeeMachineTest {
	/**
	 * Rule: for every write its name
	 */
	@Rule
	public MethodRule watch = new TestWatchman() {
	   public void starting(FrameworkMethod method) {
	      System.out.println("\n" + "***" + method.getName() + "***");
	   }
	};

	private CoffeeMachine m;

	private boolean on = true;
	private boolean off = false;
	

	/**
	 * Set the CoffeeMachine
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {
		
		m = new CoffeeMachine();
	}
	
	/**
	 * Tests no Pressed Buttons
	 */
	@Test  
	public void testStarter() {
		assertThat(m.getCurrentState2(), instanceOf(Standby.class));
	}
	
	

	/**
	 * Test a Standby button in the Standby state
	 */
	@Test  
	public void testStandbyButton() {
		m.standbyButtonPressed();
		assertThat(m.getCurrentState2(), instanceOf(Wartend.class));
	}
	
	/**
	 * Test the Power Button in the Standby state
	 */
	@Test   (expected = IllegalStateException.class)
	public void testPowerButton() {
		m.powerButtonPressed();
	}
	
	
	/**
	 * Test the Cleaning Button in the Standby state
	 */
	@Test   (expected = IllegalStateException.class)
	public void testCleaningButtonInStandby() {
		m.cleaningButtonPressed();
	}
	
	/**
	 * Test the Coffee Button in the Standby state
	 */
	@Test   (expected = IllegalStateException.class)
	public void testCoffeeButtonInStandby() {
		m.coffeeButtonPressed();
	}
	
	/**
	 * Test the Machine after the power Button was pressed
	 */
	@Test   (expected = IllegalStateException.class)
	public void testAfterPower() {
		m.powerButtonPressed();
		m.standbyButtonPressed();
	}


	/**
	 * launch a wrong sequence of buttons
	 */
	@Test (expected = IllegalStateException.class)
	public void testWrongPath() {
		m.standbyButtonPressed();
		m.cleaningButtonPressed();
		m.cleaningButtonPressed();
		m.coffeeButtonPressed();
		m.standbyButtonPressed();
		m.coffeeButtonPressed();
				
	}

	/**
	 * Switch is on. Test the Entry of the New State
	 */
	@Test 
	public void testSwitchOnEntry() {
		m.setSwitchOn(on);
		m.standbyButtonPressed();
		m.cleaningButtonPressed();
		m.coffeeButtonPressed();
		assertThat(m.getCurrentState(), instanceOf(NewState.class));
	}
	
	
	/**
	 * Switch is on. Test the Exit of the New State
	 */
	@Test
	public void testSwitchOnExit() {
		m.setSwitchOn(on);
		m.standbyButtonPressed();
		m.cleaningButtonPressed();
		m.coffeeButtonPressed();
		m.coffeeButtonPressed();
		assertThat(m.getCurrentState(), instanceOf(Wartend.class));
		
	}
	/**
	 * Tests two Switching
	 */
	@Test  
	public void testOnOff() {
		m.setSwitchOn(on);
		m.standbyButtonPressed();
		m.cleaningButtonPressed();
		m.coffeeButtonPressed();
		m.coffeeButtonPressed();
		m.setSwitchOn(off);
		m.cleaningButtonPressed();
		assertThat(m.getCurrentState(), instanceOf(Reinigung.class));

	}
	
	
	/**
	 * Test the behavior of the Machine when the Switch is On and later Off
	 */
	@Test  (expected = IllegalStateException.class)
	public void testOnOffWrongPath() {
		m.setSwitchOn(on);
		m.standbyButtonPressed();
		m.cleaningButtonPressed();
		m.coffeeButtonPressed();
		m.coffeeButtonPressed();
		m.setSwitchOn(off);
		m.cleaningButtonPressed();
		m.coffeeButtonPressed();
	}


}
