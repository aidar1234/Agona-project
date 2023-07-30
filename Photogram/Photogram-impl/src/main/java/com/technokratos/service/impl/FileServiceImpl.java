package com.technokratos.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.technokratos.exception.BadRequestException;
import com.technokratos.exception.FileNotFoundException;
import com.technokratos.exception.InternalServerError;
import com.technokratos.exception.PublicationNotFoundException;
import com.technokratos.model.jooq.enums.PublicationType;
import com.technokratos.model.jooq.tables.pojos.AccountImageEntity;
import com.technokratos.model.jooq.tables.pojos.PublicationEntity;
import com.technokratos.model.jooq.tables.pojos.PublicationImageEntity;
import com.technokratos.model.jooq.tables.pojos.PublicationVideoEntity;
import com.technokratos.repository.AccountImageRepository;
import com.technokratos.repository.PublicationImageRepository;
import com.technokratos.repository.PublicationVideoRepository;
import com.technokratos.service.FileService;
import com.technokratos.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final PublicationService publicationService;
    private final AccountImageRepository accountImageRepository;
    private final PublicationImageRepository publicationImageRepository;
    private final PublicationVideoRepository publicationVideoRepository;
    private final AmazonS3 s3Client;
    private final Tika tika;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @Value("${s3.images-directory-name}")
    private String imageDirectoryName;

    @Value("${s3.videos-directory-name}")
    private String videoDirectoryName;

    @Override
    public void createPublicationImages(UUID publicationId, UUID accountId, List<MultipartFile> files) {
        PublicationEntity publication = publicationService.findPublicationById(publicationId)
                .orElseThrow(PublicationNotFoundException::new);

        if (!publication.getAccountId().equals(accountId)) {
            throw new BadRequestException("Publication does not belong to this user");
        }

        if (!publication.getType().equals(PublicationType.IMAGE)) {
            throw new BadRequestException("Incorrect publication type");
        }

        if (!findPublicationImagesByPublicationId(publicationId).isEmpty()) {
            throw new BadRequestException("Images already exists");
        }

        for (MultipartFile file : files) {
            String extension = getExtension(file);
            PublicationImageEntity publicationImage = PublicationImageEntity.builder()
                    .extension(extension)
                    .size(file.getSize())
                    .publicationId(publicationId)
                    .build();

            UUID id = publicationImageRepository.save(publicationImage);

            try {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, imageDirectoryName + id + "." + extension, file.getInputStream(), new ObjectMetadata());
                s3Client.putObject(putObjectRequest);
            } catch (IOException e) {
                throw new InternalServerError();
            }
        }
    }

    @Override
    public void createPublicationVideo(UUID publicationId, UUID accountId, MultipartFile file) {
        PublicationEntity publication = publicationService.findPublicationById(publicationId)
                .orElseThrow(PublicationNotFoundException::new);

        if (!publication.getAccountId().equals(accountId)) {
            throw new BadRequestException("Publication does not belong to this user");
        }

        if (!publication.getType().equals(PublicationType.VIDEO)) {
            throw new BadRequestException("Incorrect publication type");
        }

        if (!findPublicationVideoByPublicationId(publicationId).isEmpty()) {
            throw new BadRequestException("Video already exists");
        }

        String extension = getExtension(file);
        PublicationVideoEntity publicationVideo = PublicationVideoEntity.builder()
                .extension(extension)
                .size(file.getSize())
                .publicationId(publicationId)
                .build();

        UUID id = publicationVideoRepository.save(publicationVideo);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, videoDirectoryName + id + "." + extension, file.getInputStream(), new ObjectMetadata());
            s3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new InternalServerError();
        }
    }

    @Override
    public List<PublicationImageEntity> findPublicationImagesByPublicationId(UUID id) {
        return publicationImageRepository.findPublicationImageListByPublicationId(id);
    }

    @Override
    public List<PublicationVideoEntity> findPublicationVideoByPublicationId(UUID id) {
        return publicationVideoRepository.findPublicationVideoListByPublicationId(id);
    }

    @Override
    public void deleteImagesByPublicationId(UUID id) {
        List<PublicationImageEntity> images = publicationImageRepository.findPublicationImageListByPublicationId(id);

        for (PublicationImageEntity image : images) {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, imageDirectoryName + image.getId() + "." + image.getExtension());
            s3Client.deleteObject(deleteObjectRequest);
        }

        publicationImageRepository.deleteByPublicationId(id);
    }

    @Override
    public void deleteVideoByPublicationId(UUID id) {
        List<PublicationVideoEntity> videos = publicationVideoRepository.findPublicationVideoListByPublicationId(id);

        for (PublicationVideoEntity video : videos) {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, videoDirectoryName + video.getId() + "." + video.getExtension());
            s3Client.deleteObject(deleteObjectRequest);
        }

        publicationVideoRepository.deleteByPublicationId(id);
    }

    @Override
    public String updateAccountImageByAccountId(UUID id, MultipartFile file) {
        Optional<AccountImageEntity> optionalImage = accountImageRepository.findByAccountId(id);
        if (optionalImage.isEmpty()) {
            String extension = getExtension(file);
            AccountImageEntity image = AccountImageEntity.builder()
                    .accountId(id)
                    .size(file.getSize())
                    .extension(extension)
                    .build();

            UUID uuid = accountImageRepository.save(image);

            try {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, imageDirectoryName + uuid + "." + extension, file.getInputStream(), new ObjectMetadata());
                s3Client.putObject(putObjectRequest);
            } catch (IOException e) {
                throw new InternalServerError();
            }
            return uuid + "." + extension;
        } else {
            AccountImageEntity image = optionalImage.get();

            String oldExtension = image.getExtension();

            String extension = getExtension(file);

            if (!oldExtension.equals(extension)) {
                DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, imageDirectoryName + image.getId() + "." + image.getExtension());
                s3Client.deleteObject(deleteObjectRequest);
            }

            image.setExtension(extension);
            image.setSize(file.getSize());

            accountImageRepository.update(image);

            try {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, imageDirectoryName + image.getId() + "." + image.getExtension(), file.getInputStream(), new ObjectMetadata());
                s3Client.putObject(putObjectRequest);
            } catch (IOException e) {
                throw new InternalServerError();
            }

            return image.getId() + "." + image.getExtension();
        }
    }

    @Override
    public String getAccountImageNameByAccountId(UUID id) {
        Optional<AccountImageEntity> optionalImage = accountImageRepository.findByAccountId(id);
        if (optionalImage.isEmpty()) {
            return "";
        } else {
            return optionalImage.get().getId() + "." + optionalImage.get().getExtension();
        }
    }

    @Override
    public Resource getImageByName(String imageName) {
        GetObjectRequest getObjectRequest = new GetObjectRequest("photogram", imageDirectoryName + imageName);
        try {
            S3Object object = s3Client.getObject(getObjectRequest);
            return new InputStreamResource(object.getObjectContent());
        } catch (AmazonS3Exception e) {
            throw new FileNotFoundException();
        }
    }

    @Override
    public Resource getVideoStreamByName(String videoName) {
        GetObjectRequest getObjectRequest = new GetObjectRequest("photogram", videoDirectoryName + videoName);
        try {
            S3Object object = s3Client.getObject(getObjectRequest);
            return new InputStreamResource(object.getObjectContent());
        } catch (AmazonS3Exception e) {
            throw new FileNotFoundException();
        }
    }

    private String getExtension(MultipartFile file) {
        try {
            return tika.detect(file.getInputStream()).replaceFirst(".*/", "");
        } catch (IOException e) {
            throw new InternalServerError();
        }
    }
}
