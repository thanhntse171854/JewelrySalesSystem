package com.swp391.JewelrySalesSystem.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.swp391.JewelrySalesSystem.config.CloudinaryConfig;
import com.swp391.JewelrySalesSystem.service.CloudinaryService;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
  private final CloudinaryConfig cloudinaryConfig;

  @Override
  public String uploadImage(byte[] image) throws IOException {
    Map uploadResult =
        cloudinaryConfig.cloudinary().uploader().upload(image, ObjectUtils.emptyMap());
    return (String) uploadResult.get("url");
  }
}
