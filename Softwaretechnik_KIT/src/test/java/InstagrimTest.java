

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jis.Main;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Gil Baram
 *
 */
public class InstagrimTest {

	private Main m;

	private Instagrim i = new Instagrim();

	/**
	 * Set the current username
	 * @throws Exception 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		System.setProperty("user.name", "uteld");
	}
	
	
	/**
	 * Test Method for init
	 */
	@Test
	public void testInit1() {
		i.init(m);
	}
	
	
	/**
	 * Second test Method for init, to check if it accepts correctly the given Error output 
	 */
	@Test
	public void testInit2() {
		final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));
		String str = "iMage: Der Bildverschoenerer, dem Influencer vertrauen! "
				+ "Jetzt bist auch Du Teil unseres Teams, uteld.";
		i.init(m);
		assertEquals(str, errContent.toString());
	}
	
	/**
	 * Test Method for configure()
	 */
	@Test
	public void testConfigure() {
		i.configure();

	}
	
	/**
	 * Test Method for run()
	 */
	@Test
	public void testRun() {
		i.run();

	}
	
	

}
