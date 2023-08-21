import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.kohsuke.MetaInfServices;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Gil Baram
 *
 */


@MetaInfServices
public class Instagrim extends PluginForJmjrst {
	private static Main m;
	private static String name = "Instagrim";
	private static final String RES = "src\\main\\resources\\CommentList.txt";
	/**
	 * Standard Error Massage
	 */
	public static final String ERRMESSAGE =
			"iMage: Der Bildverschoenerer, dem Influencer vertrauen! Jetzt bist auch Du Teil unseres Teams, " 
					+ System.getProperty("user.name") + ".";
	

	/**
	 * Return the name of the Plugin
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Initialize the PlugIn
	 */
	@Override
	public void init(Main main) {
		System.err.print(ERRMESSAGE);
		System.out.println();
	}

	/**
	 * Run the Plugin
	 */
	@Override
	public void run() {
		File file = new File(RES);
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.err.print("File Not Found");
			e.printStackTrace();
		}
		ArrayList<String> commentsList = new ArrayList<String>();
		while (s.hasNext()) {
			commentsList.add(s.nextLine());
		}
		JOptionPane.showMessageDialog(m, new Instagrim().getComment(commentsList));
	}

	 /**
	  * get a random comment from the list
	 * @param list a given list of String
	 * @return a random comment
	 */
	
	private String getComment(ArrayList<String> list) {
		return list.get(new Random().nextInt(list.size()));
	}

	/**
	 *Checks if the PlugIn is configurable
	 */
	@Override
	public boolean isConfigurable() {
		return true;
	}

	/**
	 * configure the PlugIn
	 */
	@Override
	public void configure() {
		if (isConfigurable()) {
			File file = new File(RES);
			Scanner s = null;
			String listString = "";
			try {
				s = new Scanner(file);
			} catch (FileNotFoundException e) {
				System.err.print("File Not Found");
				e.printStackTrace();
			}
			while (s.hasNext()) {
				listString = listString + "\n" + s.nextLine();
			}
			JOptionPane.showMessageDialog(m, listString);
		}
		
	}

}
