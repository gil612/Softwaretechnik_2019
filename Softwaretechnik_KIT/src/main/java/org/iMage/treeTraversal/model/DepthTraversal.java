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

public class DepthTraversal {
	private ArrayList<Tree> treelist = new ArrayList<Tree>();

	public ArrayList<Tree> getTreelist() {
		return treelist;
	}

	public void setTreelist(ArrayList<Tree> treelist) {
		this.treelist = treelist;
	}
	
	public DepthTraversal(File file) throws NoSuchFileException {
		if (!file.exists()) {
			throw new NoSuchFileException("no such file exception!");
		} 
	}

	public Iterator<Tree> getIt(Tree parent) {
		treelist.add(parent);
		iteratormethod(parent);
		Iterator<Tree> iter = treelist.iterator();
		return iter;
		}

	private void iteratormethod(Tree parent) {
		ArrayList<Tree> list = new ArrayList<Tree>();	
		Path p = parent.getFile().toPath();
		try (DirectoryStream<Path> files = Files.newDirectoryStream(p)) {		
		for (Path found : files) {
			if (Files.isDirectory(found)) {
				AbstractTree at = new AbstractTree(found.toFile(), parent) { };
				list.add(at);
			}	
		}
		} catch (IOException e) {
				e.printStackTrace();
		}
		Iterator<Tree> it = list.iterator();
		while (it.hasNext()) {
			Tree parent2 = it.next();
			treelist.add(parent2);
			iteratormethod(parent2);
		}
	}
	
	public void removeTree(Tree tr) {
		System.out.println(tr.getFile());
		int n = treelist.size();
		for (int i = 0; i < n ; i++) {
			if (treelist.get(i).getFile().equals(tr.getFile())) {
				treelist.remove(i);
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
