package com.example.cloudinary;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
	public Map<String, Object> uploadMedia(MultipartFile file);

	public Map<String, Object> updateMedia(MultipartFile file, String publicId);

	public Map<String, Object> deleteMedia(String publicId, String resourceType);
}
