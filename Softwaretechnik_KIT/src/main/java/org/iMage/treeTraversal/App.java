package org.iMage.treeTraversal;

import java.io.File;
import java.nio.file.NoSuchFileException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.iMage.treeTraversal.model.BreadthTraversal;
import org.iMage.treeTraversal.model.DepthTraversal;
import org.iMage.treeTraversal.runners.Runner;
import org.iMage.treeTraversal.runners.SubRunner;


public class App {
	private static final String DIRECTORY_OPT = "d";
	  /**
	   * Defines the option to use JPGs (no parameter).<br>
	   * This is not compatible with {@value #PNG_OPT}.
	   */
	  private static final String JPG_OPT = "j";
	  /**
	   * Defines the option to use PNGs (no parameter).<br>
	   * This is not compatible with {@value #JPG_OPT}.
	   */
	  private static final String PNG_OPT = "p";
	  /**
	   * Defines the option to use BreadthTraversal (no parameter).<br>
	   * Iff not set use DepthTraversal.
	   */
	  private static final String BFS_OPT = "b";
	  private static String jpgorpng = ".jpg";
	  private static String loc = "src\\main\\resources\\dir\\1";
	  private App() {
		    throw new IllegalAccessError();
		  }

	public static void main(String[] args) {
		CommandLine cmd = null;
	    try {
	      cmd = App.doCommandLineParsing(args);
	    } catch (ParseException e) {
	      System.err.println("Wrong command line arguments given: " + e.getMessage());
	      System.exit(1);
	    }
	    
	    Class depth = DepthTraversal.class;
		Class breadth = BreadthTraversal.class;
		if (cmd.hasOption(App.PNG_OPT)) {
			jpgorpng = ".png";
		}	
		Runner r = new SubRunner();
		
		File file = new File(loc);

		if (cmd.hasOption(App.BFS_OPT)) {
			try {
				r.run(file, breadth);
			} catch (NoSuchFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				r.run(file, depth);
			} catch (NoSuchFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
	    Options options = new Options();
	    Option opt;

	    /*
	     * Define command line options and arguments
	     */
	    opt = new Option(App.DIRECTORY_OPT, "dir", true, "path to start directory");
	    opt.setRequired(true);
	    opt.setType(String.class);
	    options.addOption(opt);

	    opt = new Option(App.JPG_OPT, "jpg", false, "show jpgs");
	    opt.setRequired(false);
	    options.addOption(opt);

	    opt = new Option(App.PNG_OPT, "png", false, "show pngs");
	    opt.setRequired(false);
	    options.addOption(opt);

	    opt = new Option(App.BFS_OPT, "bfs", false, "use bfs");
	    opt.setRequired(false);
	    options.addOption(opt);

	    CommandLineParser parser = new DefaultParser();
	    return parser.parse(options, args);
	  }
	public static String getJpgorpng() {
		return jpgorpng;
	}

}
