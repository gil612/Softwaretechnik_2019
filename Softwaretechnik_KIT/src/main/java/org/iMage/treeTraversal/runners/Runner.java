package org.iMage.treeTraversal.runners;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.iMage.treeTraversal.model.AbstractTree;
import org.iMage.treeTraversal.model.DepthTraversal;
import org.iMage.treeTraversal.model.Tree;
import org.iMage.treeTraversal.traverser.Traversal;

/**
 * A runner extracts files by using {@link Tree Trees}.
 */
public abstract class Runner {
	
  /**
   * Execute the runner.
   *
   * @param startFolder
   *          the start folder
   * @param traversalClass
   *          the traversal class
 * @throws NoSuchFileException 
   */
  public final void run(File startFolder, Class<? extends Traversal> traversalClass) throws NoSuchFileException {
    Tree tree = this.buildFolderStructure(startFolder);
    List<File> files = this.getFiles(tree, traversalClass);
    List<File> selectedFiles = this.selectFiles(files);
    this.printResults(selectedFiles);
  }

  /**
   * Builds a {@link Tree} starting with a start folder.
   *
   * @param startFolder
   *          the start folder
   * @return the {@link Tree}
   */
  private Tree buildFolderStructure(File startFolder) {
	  AbstractTree at = new AbstractTree(startFolder) {
		};
		
		return at;
  }

  private void display(List<File> filelist) {
	  Iterator<File> it = filelist.iterator();
	  while (it.hasNext()) {
		  System.out.println(it.next());
	  }
	
}

/**
   * Get a list of all files using the iterator defined by {@link Traversal}.
   *
   * @param tree
   *          the tree
   * @param traversalClass
   *          the traversal
   * @return the list of traversed files (in order of traversal)
 * @throws NoSuchFileException 
   */
  private List<File> getFiles(Tree tree, Class<? extends Traversal> traversalClass) throws NoSuchFileException {
	  Iterator<Tree> it = tree.getIterator(traversalClass);
	  List<File> filelist = new ArrayList<File>();
	  while (it.hasNext()) {
		  Tree t = it.next();
		  Path p = t.getFile().toPath();
		  try (DirectoryStream<Path> files = Files.newDirectoryStream(p)) {
		  
		for (Path found : files) {
			if ((Files.isRegularFile(found)) && 
					((found.toString().toLowerCase().endsWith(".jpg")) || 
							(found.toString().toLowerCase().endsWith(".png")))) {
				filelist.add(found.toFile());
			}
		  
		}
		
		  } catch (IOException e) {
		  }

		  }
	return filelist;
  }

  private void display(Iterator<Tree> it) {
	  
	  while (it.hasNext()) {
			System.out.println(it.next().getFile());
		}
	
}

/**
   * Filter files according to strategy.
   *
   * @param files
   *          the files
   * @return the filtered list of files
   */
  protected abstract List<File> selectFiles(List<File> files);

  /**
   * Print a list of files to stdout.
   * 
   * @param selectedFiles
   *          the list of files
   */
  private void printResults(List<File> selectedFiles) {

	  for (File f: selectedFiles) {
		  System.out.println(f);
	  }
  }

}
