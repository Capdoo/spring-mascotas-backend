package com.mascotas.app.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Service
public class FileUploadService {

	private String name;

	public FileUploadService() {
		super();

	}
	
	public String fileUpload(byte[] imagen) throws IOException {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", "unmsm234",
				"api_key", "872387794637319",
				"api_secret", "xF-9FwzZamaUbqtmWNbBcweiJoU",
				"secure", true));
		File file = new File("my_image.jpg");
		
		try (FileOutputStream outputStream = new FileOutputStream(file)){
			outputStream.write(imagen);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		return (String) uploadResult.get("url");
	}
	
	public byte[] convertStringToBytes(String encoded) {
		return Base64.getDecoder().decode(encoded);
	}
	
	public String obtenerEncoded(String brute) {
		String partSeparator = ",";
		if (brute.contains(partSeparator)) {
		  String encodedImg = brute.split(partSeparator)[1];
		  return encodedImg;
		}else {
			return brute;
		}
		
	}
	

	
}
