package vn.techmaster.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.image.entity.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByOrderByCreatedAtDesc();
}