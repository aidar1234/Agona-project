package com.technokratos.service;

import com.technokratos.model.jooq.tables.pojos.PublicationImageEntity;
import com.technokratos.model.jooq.tables.pojos.PublicationVideoEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {

    void createPublicationImages(UUID publicationId, UUID accountId, List<MultipartFile> files);

    void createPublicationVideo(UUID publicationId, UUID accountId, MultipartFile files);

    List<PublicationImageEntity> findPublicationImagesByPublicationId(UUID id);

    List<PublicationVideoEntity> findPublicationVideoByPublicationId(UUID id);

    void deleteImagesByPublicationId(UUID id);

    void deleteVideoByPublicationId(UUID id);

    String updateAccountImageByAccountId(UUID id, MultipartFile file);

    String getAccountImageNameByAccountId(UUID id);

    Resource getImageByName(String imageName);

    Resource getVideoStreamByName(String videoName);
}
