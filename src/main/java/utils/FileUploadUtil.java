package utils;

import java.io.*;
import java.nio.file.*;
import javax.xml.bind.DatatypeConverter;

import entities.Movie;
import exceptions.movie.ImageException;
 
public class FileUploadUtil {    // add image to a movie
    public static Movie saveFile(String file, Movie movie, String type)  throws IOException{
    	if(!file.contains("data:image/")) {
    		throw new ImageException("Could not save " + type + ".");
    	}
    	String[] result = file.split(";base64,");
    	byte[] bytes = DatatypeConverter.parseBase64Binary(result[1]);
    	String extension = result[0].replace("data:image/", "");
    	String webPath = movie.getId() + type + "." + extension;
    	System.out.println(result[0]);
		Path path = Paths.get("src", "main", "resources", "static", "images", webPath);
		try {
			Files.write(path, bytes);
			if(type.equals("image")) movie.setImage("images/" + webPath);
			if(type.equals("landscape")) movie.setLandscape("images/" + webPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ImageException("Could not save " + type + ".");
		}
    	return movie;
    }
}
