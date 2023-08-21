package org.iMage.treeTraversal.runners;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.iMage.treeTraversal.App;

/**
 * @author Owner
 *
 */
public class SubRunner extends Runner {
	
	
	/**
	 * 
	 */
	public SubRunner() {
		
	}
	
	protected List<File> selectFiles(List<File> files) { List<File> fin = new ArrayList<File>();
		  Iterator<File> fileIt = files.iterator();
		  while (fileIt.hasNext()) {
			  File f = fileIt.next();
			  if (f.toPath().toString().toLowerCase().endsWith(App.getJpgorpng())) {
				  fin.add(f);
			  }
			  
		  }
		  fileIt = fin.iterator();
		return fin;
	}

}
