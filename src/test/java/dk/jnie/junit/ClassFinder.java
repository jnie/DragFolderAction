package dk.jnie.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import lti.java.jcf.JcfClassFile;
import lti.java.jcf.JcfClassInputStream;

/**
 * This Class will find all .class files in the root folder and sub folders.
 *  
 * @author jniels23
 *
 */
public class ClassFinder {

	final private Vector<String> classNameList = new Vector<String>();
	
	public ClassFinder(final File classPathRoot) throws FileNotFoundException, IOException {
		findAndStoreTestClassFiles(classPathRoot);
	}

	private void findAndStoreTestClassFiles(File folder) throws FileNotFoundException, IOException {
		String sFiles[] = folder.list();

		if (sFiles == null) {
			throw new FileNotFoundException("No files or folders found. Using: " + folder.getAbsolutePath());
		}
		
		for (String sFile: sFiles) {
			File file = new File(folder,sFile);
			if (file.isDirectory()) {
				findAndStoreTestClassFiles(file);
			} else {
				String fileName = file.getName();
				int extension_index = fileName.indexOf(".class");
				if (extension_index != -1) {
					JcfClassInputStream inputStream = new JcfClassInputStream(new FileInputStream(file));
					JcfClassFile classFile = new JcfClassFile (inputStream);
		            System.out.println ("Processing: " + classFile.getFullName ().replace ('/','.'));
		            classNameList.add(classFile.getFullName ().replace ('/','.'));
				}
			}
		}
	}
	
	public Vector<String> getClasses() {
		return classNameList;
	}
	
	
	public static void main(String[] args) {
		File file = new File("C:\\projects\\workspace\\Android\\DragFolderAction\\target\\classes");
		ClassFinder cf = null;
		try {
			cf = new ClassFinder(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (cf != null) {
			Vector<String> classes = cf.getClasses();
			for (String className:classes) {
				System.out.println(className);
			}
		}
	}
	
}
