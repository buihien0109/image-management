package vn.techmaster.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.image.entity.Image;
import vn.techmaster.image.exception.NotFoundException;
import vn.techmaster.image.repository.ImageRepository;
import vn.techmaster.image.utils.FileUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileUtils fileUtils;

    public List<Image> getAllImage() {
        return imageRepository.findByOrderByCreatedAtDesc();
    }

    public Image getImage(Integer id) {
        return imageRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found image with id = " + id);
        });
    }

    public Image uploadImage(MultipartFile file) {
        fileUtils.validateFile(file);

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Image image = new Image(fileName, file.getContentType(), file.getBytes());
            return imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("Upload image error");
        }
    }

    public void deleteImage(Integer id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found image with id = " + id);
        });

        imageRepository.delete(image);
    }
}
