package org.iMage.treeTraversal.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class BreadthTraversal {
	private Tree tree;
	private ArrayList<Tree> finallist = new ArrayList<Tree>();
	private ArrayList<Tree> queue = new ArrayList<Tree>();
	private ArrayList<Tree> list = new ArrayList<Tree>();

	public BreadthTraversal(File file) throws NoSuchFileException {
		if (!file.exists()) {
			throw new NoSuchFileException("no such file exception!");
		} 
	}

	public Iterator<Tree> getIt(Tree parent) {
		finallist.add(parent);
		queue.add(parent);
		iteratormethod();
		Iterator<Tree> iter = finallist.iterator();
		return iter;
	}

	private void iteratormethod() {
		Path p = queue.get(0).getFile().toPath();
		queue.remove(0);
		list.clear();
		try (DirectoryStream<Path> files = Files.newDirectoryStream(p)) {		
			for (Path found : files) {
				if (Files.isDirectory(found)) {
					AbstractTree at = new AbstractTree(found.toFile(),tree) { };
					list.add(at);	
			} 
			}
		} catch (IOException e) {e.printStackTrace();}
		finallist.addAll(list);
		queue.addAll(list);		
		if (!queue.isEmpty()) {
			iteratormethod();
		}
		
	}
	
	public ArrayList<Tree> getFinallist() {
		return finallist;
	}

	public void setFinallist(ArrayList<Tree> finallist) {
		this.finallist = finallist;
	}

	public void removeTree(Tree tr) {
		System.out.println(tr.getFile());
		int n = finallist.size();
		for (int i = 0; i < n ; i++) {
			if (finallist.get(i).getFile().equals(tr.getFile())) {
				finallist.remove(i);
				break;
			}
		}
	}
	public void deleteTree(Tree tr) throws IOException {
	if (tr.getFile().exists()) {
			FileUtils.deleteDirectory(tr.getFile());
		} else {
			throw new NoSuchFileException("no such file exception!");
		}
	}

}
