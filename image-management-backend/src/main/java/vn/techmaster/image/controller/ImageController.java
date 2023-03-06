package vn.techmaster.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.image.entity.Image;
import vn.techmaster.image.service.ImageService;

@RestController
@RequestMapping("api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    // Lấy danh sách ảnh
    @GetMapping("")
    public ResponseEntity<?> getAllImage() {
        return ResponseEntity.ok(imageService.getAllImage());
    }

    // Xem ảnh
    @GetMapping("{id}")
    public ResponseEntity<?> readImage(@PathVariable Integer id) {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .body(image.getData());
    }

    // Upload ảnh
    @PostMapping("")
    public ResponseEntity<?> uploadImage(@ModelAttribute("file") MultipartFile file) {
        return new ResponseEntity<>(imageService.uploadImage(file), HttpStatus.CREATED);
    }

    // Download ảnh
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Integer id) {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }

    // Xóa ảnh
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Integer id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build(); // 204
    }
}

