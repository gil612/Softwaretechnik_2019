package org.iMage.treeTraversal.model;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.Objects;

import org.iMage.treeTraversal.traverser.Traversal;

/**
 * Defines an abstract tree element.
 */
public abstract class AbstractTree implements Tree {

	private final File file;
	private final Tree parent;

	/**
	 * Create by file and parent.
	 *
	 * @param file
	 *          the file
	 * @param parent
	 *          the parent
	 */
	protected AbstractTree(File file, Tree parent) {
		this.file = Objects.requireNonNull(file, "file cannot be null");
		this.parent = parent;
	}

	/**
	 * Create by file (without parent).
	 *
	 * @param file
	 *          the file
	 */
	protected AbstractTree(File file) {
		this(file, null);
	}

	@Override
	public final File getFile() {
		return this.file;
	}

	@Override
	public final Tree getParent() {
		return this.parent;
	}

	@Override
	public final boolean hasParent() {
		return !Objects.isNull(this.parent);
	}

	@Override
	public final Iterator<Tree> getIterator(Class<? extends Traversal> traversal) throws NoSuchFileException {
		Class<BreadthTraversal> breadth = BreadthTraversal.class;
		Class<DepthTraversal> depth = DepthTraversal.class;
		 Iterator<Tree> it = null;
		 if (traversal.getName().equals(breadth.getName())) {
			 BreadthTraversal bt = new BreadthTraversal(file);
			 it = bt.getIt(this);		
		 }	 
		 if (traversal.getName().equals(depth.getName())) {
			 DepthTraversal dt = new DepthTraversal(file);
			  it = dt.getIt(this);
		 }
		return it;
		
	}
}
