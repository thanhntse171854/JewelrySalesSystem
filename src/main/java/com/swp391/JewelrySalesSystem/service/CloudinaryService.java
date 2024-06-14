package com.swp391.JewelrySalesSystem.service;

import java.io.IOException;

public interface CloudinaryService {
  String uploadImage(byte[] image) throws IOException;
}
