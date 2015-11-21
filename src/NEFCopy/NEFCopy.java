package NEFCopy;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.filechooser.FileNameExtensionFilter;


public class NEFCopy {	
	static String jpegPath = System.getProperty("user.home") + "/" + "Desktop/Up";
	static String extension = "jpg";
	static String fnError = "";
	static int successfulCase = 0;
	static int totalCase = 0;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();

		if(args.length == 1){
			copyProcess(args[0], jpegPath, "NEF FOR EDITING");
			
		}
		else if(args.length == 2){
			copyProcess(args[0], args[1], "NEF FOR EDITING");
		}
		else if(args.length == 3){
			copyProcess(args[0], args[1], args[2]);
		}

		long end = System.currentTimeMillis();
		long total = (end - start)/1000;
		
		System.out.println("\n");
		System.out.println("Succeed " + successfulCase + "/" + totalCase);
		System.out.println("Running time " + total + "s");
		
		if(fnError.length()>1){
			System.out.println("Error: \n" + fnError);
		}
	}
	
	private static void copyProcess(String nefPath, String jpegPath, String nefStoreFolderName) throws IOException{		
		// Create a new folder
		new File(jpegPath+"/"+nefStoreFolderName).mkdir();
		File desFolder = new File(jpegPath+ "/" + nefStoreFolderName);
		
		// Check the JPEG file
		File filteredFolder = new File(jpegPath);
		File [] listOfJPEG = filteredFolder.listFiles();
		
		File nefFolder = new File(nefPath);
		File [] listOfNEF = nefFolder.listFiles();
		
		
		// Find the position of extension dot
		int dotIdx = -1;
		String fileName = "";
		
		for(File file : listOfJPEG){
			if(file.isFile() && file.getName().toLowerCase().endsWith(extension.toLowerCase())){
				totalCase++;
				System.out.print("*");				
			}		
		}
		System.out.println("");
		
		for(File file : listOfJPEG){
			// Get only if it's a file and has the extension as you want
			if(file.isFile() && file.getName().toLowerCase().endsWith(extension.toLowerCase())){
				// Find the position of extension dot and get the filename
				dotIdx = file.getName().lastIndexOf(".");	
				fileName = file.getName().substring(0, dotIdx);	
				
				// We have DSC_0846 (without extension). Now, check if the corresponding NEF existed
				File sourceNEFFile = new File (nefFolder+"/"+fileName+".NEF");
				File desNEFFile = new File (desFolder + "/"+fileName+".NEF");
				
				
				if(sourceNEFFile.exists()){	
					// Start copy from source to destination
					Files.copy(sourceNEFFile.toPath(), desNEFFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					
					System.out.print("*");
					successfulCase++;
				}
				else{
					System.out.print("*");
					fnError += "File not found " + fileName +".NEF \n"; 
				}
				
			}
		}
	}

}