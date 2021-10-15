package mx.com.naat.news.infrastructure.adapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mx.com.naat.news.exception.ImageContentTypeException;

@Service
public class ServiceUpload {

	private static String uploadPath = "./uploads";
	
	public static String save(MultipartFile file) {
		
		String ruta = generateImage(file);
		
		try {
			Path root = Paths.get(uploadPath);

			Files.copy(file.getInputStream(), root.resolve(ruta));
		} catch (Exception e) {
			throw new RuntimeException("No se puede guardar la imagen. Error: " + e.getMessage());
		}

		return ruta;
	}

	private static String generateImage(MultipartFile image) {
		String ext = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
		if (!ext.equals(".jpeg")) {
			throw new ImageContentTypeException();
		}
		return UUID.randomUUID().toString() + ext;
	}
	
	public static void delete(String url) {
		String[] separador = url.split("/");
		try {
			Path root = Paths.get(uploadPath);
			System.out.println(separador[4]);
			Files.deleteIfExists(root.resolve(separador[4]));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}