package com.swp391.JewelrySalesSystem.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.swp391.JewelrySalesSystem.config.CloudinaryConfig;
import com.swp391.JewelrySalesSystem.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
  private final CloudinaryConfig cloudinaryConfig;

  @Override
  public String uploadImage(byte[] image) {
    var params =
        ObjectUtils.asMap(
            "folder", "test",
            "resource_type", "image");
    try {
      var uploadResult = cloudinaryConfig.cloudinary().uploader().upload(image, params);
      return uploadResult.get("secure_url").toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
