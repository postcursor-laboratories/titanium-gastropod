import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;


public class ImageLoader {
	static ImageLoader INST;
	
	HashMap<String,BufferedImage> imageList;
	public ImageLoader(){
		INST = this;
		loadAllImages();
	}
	
	public BufferedImage get(String name){
		try{
			BufferedImage bi=imageList.get(name);
			if(bi==null)
				throw new IllegalArgumentException();
			return bi;
		}catch(IllegalArgumentException ife){
			throw new RuntimeException("Didn't have loaded necessary image "+name+".png",ife);
		}
	}
	
	ArrayList<String> loadAllImages(){
		if(isJar()){
			return loadAllImagesJar();
		} else {
			return loadAllImagesDirectory();
		}
	}
	
	boolean isJar(){
		return Game.class.getResource("Game.class").toString().startsWith("jar");
	}
	
	String jarLocation(){
		String absoluteName=Game.class.getResource("Game.class").toString();
		int end=absoluteName.indexOf("!");
		int start="jar:file:".length();
		return absoluteName.substring(start, end);
	}
	
	ArrayList<String> loadAllImagesJar(){
		System.out.println("Loading from jar");
		ArrayList<String> fileList = new ArrayList<>();
		JarFile jar;
		try {
			jar = new JarFile(jarLocation());
			for(Enumeration<JarEntry> jeEnum = jar.entries(); jeEnum.hasMoreElements(); ){
				JarEntry curr = jeEnum.nextElement();
				fileList.add(curr.getName());
				loadImageFromStream(curr.getName(), jar.getInputStream(curr));
			}
			jar.close();
		} catch (IOException e) {
			System.err.println("Error reading jar file at "+jarLocation()+": "+e);
		}
		return fileList;
	}
	
	ArrayList<String> loadAllImagesDirectory(){
		System.out.println("Loading from directory");
		ArrayList<String> fileList = new ArrayList<>();

		ArrayList<String> folderList = new ArrayList<>(); //List of dirs to search. Will be added to as searched.
		folderList.add("./res/");
		for(ListIterator<String> iter=folderList.listIterator(); iter.hasNext(); ){
			File folder=new File(iter.next());
			File[] listOfFiles=folder.listFiles();
			for(int i=0;i<listOfFiles.length;i++){
				if(listOfFiles[i].isFile()){ //File or directory?
					
					String fullName=listOfFiles[i].getPath().replaceAll("\\\\","/").substring(2);
					fileList.add(fullName);
					try{
						loadImageFromStream(fullName, new FileInputStream(listOfFiles[i]));
					}catch(FileNotFoundException nfne){
						System.err.println("File "+fullName+" vanished!");
					}
					
				} else { //Add it to the queue
					iter.add(listOfFiles[i].getPath());
					iter.previous();
				}
			}
		}
		return fileList;
	}
	
	void loadImageFromStream(String fullName, InputStream src){
		if(fullName.endsWith(".png")){
			String fileName=fullName.substring(fullName.lastIndexOf("/"));
			try{
				BufferedImage img=ImageIO.read(src);
				if(img==null){ //Usually because not valid image
					System.err.println("Error loading image \""+fullName+"\": Was null");
				} else {							
					imageList.put(fullName,img);	
					if(imageList.put(fileName,img)!=null){ //Since files are list by their short name ("foo.png"), collisions are possible.
						System.err.println("Warning: Two instances of "+fileName);
					}
				}
			}catch(IOException ioe){
				System.err.println("Error loading image \""+fullName+"\": "+ioe);
			}
		}
	}
}
