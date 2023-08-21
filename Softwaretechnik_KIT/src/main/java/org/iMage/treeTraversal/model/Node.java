package org.iMage.treeTraversal.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;


/**
 * Defines a node of the tree (folder).
 */
public class Node extends AbstractTree {
  private List<Tree> children = new ArrayList<>();
  

  /**
   * Create by folder and parent.
   *
   * @param folder
   *          the folder
   * @param parent
   *          the parent
   */
  public Node(File folder, Tree parent) {
    super(folder, parent);
    Path p = parent.getFile().toPath();
	try (DirectoryStream<Path> files = Files.newDirectoryStream(p)) {	
		for (Path found : files) {
			if (Files.isDirectory(found)) {
				AbstractTree at = new AbstractTree(found.toFile(),parent) { };
				children.add(at);
			}
			
		}
	} catch (IOException e) {
		
	}
    
  }

  /**
   * Create by folder (no parent)
   *
   * @param folder
   *          the folder
   */
  public Node(File folder) {
    super(folder);
    AbstractTree at = new AbstractTree(folder) { };
    Path p = folder.toPath();
	try (DirectoryStream<Path> files = Files.newDirectoryStream(p)) {	
		for (Path found : files) {
			if (Files.isDirectory(found)) {
				AbstractTree at2 = new AbstractTree(found.toFile(),at) { };
				children.add(at2);
			}	
		}
	} catch (IOException e) {
		
	}
  }

  /**
   * Add a child to the node.
   *
   * @param child
   *          the child
 * @throws IOException 
   */
  public void addChild(Tree child) throws IOException {
      FileUtils.copyDirectoryToDirectory(child.getFile(), super.getFile());
    this.children.add(child);
  }

  /**
   * Remove a child from the node.
   *
   * @param child
   *          the child
 * @throws IOException 
   */
  public void removeChild(Tree child) throws IOException{
	  System.out.println(child.getFile());
	  FileUtils.deleteDirectory(child.getFile());
	  System.out.println(child.getFile());
    this.children.remove(child);
  }

  /**
   * Get a collection of all children (unmodifiable).
   *
   * @return a collection of all children
   */
  public Collection<Tree> getChildren() {
    return Collections.unmodifiableCollection(this.children);
  }

}
