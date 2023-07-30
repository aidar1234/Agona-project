package com.technokratos.service.impl;

import com.technokratos.dto.response.PublicationResponse;
import com.technokratos.exception.ForbiddenException;
import com.technokratos.exception.PublicationNotFoundException;
import com.technokratos.mapper.PublicationMapper;
import com.technokratos.model.jooq.enums.PublicationType;
import com.technokratos.model.jooq.tables.pojos.PublicationEntity;
import com.technokratos.repository.PublicationRepository;
import com.technokratos.service.FileService;
import com.technokratos.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    @Lazy
    @Autowired
    private FileService fileService;

    @Override
    public UUID createImagePublicationByAccountId(UUID accountId, String description, List<MultipartFile> files) {
        PublicationEntity publication = publicationMapper.getPublicationEntity(accountId, PublicationType.IMAGE, description);
        UUID id =  publicationRepository.save(publication);
        fileService.createPublicationImages(id, accountId, files);
        return id;
    }

    @Override
    public UUID createVideoPublicationByAccountId(UUID accountId, String description, MultipartFile file) {
        PublicationEntity publication = publicationMapper.getPublicationEntity(accountId, PublicationType.VIDEO, description);
        UUID id = publicationRepository.save(publication);
        fileService.createPublicationVideo(id, accountId, file);
        return id;
    }

    @Override
    public Optional<PublicationEntity> findPublicationById(UUID id) {
        return publicationRepository.findById(id);
    }

    @Override
    public List<PublicationResponse> getPublicationsByAccountId(UUID id) {
        return publicationRepository.findListByAccountId(id).stream()
                .map(publication -> {
                    List<String> fileNames;
                    if (publication.getType().equals(PublicationType.IMAGE)) {
                        fileNames = fileService.findPublicationImagesByPublicationId(publication.getId()).stream()
                                .map(publicationImage -> publicationImage.getId() + "." + publicationImage.getExtension())
                                .collect(Collectors.toList());
                    } else {
                        fileNames = fileService.findPublicationVideoByPublicationId(publication.getId()).stream()
                                .map(publicationVideo -> publicationVideo.getId() + "." + publicationVideo.getExtension())
                                .collect(Collectors.toList());
                    }
                    return publicationMapper.getPublicationResponse(publication, fileNames);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deletePublicationById(UUID id, UUID accountId) {
        PublicationEntity publication = publicationRepository.findById(id).orElseThrow(PublicationNotFoundException::new);

        if (!publication.getAccountId().equals(accountId)) {
            throw new ForbiddenException("Publication does not belong to this user");
        }

        if (publication.getType().equals(PublicationType.IMAGE)) {
            fileService.deleteImagesByPublicationId(id);
        } else {
            fileService.deleteVideoByPublicationId(id);
        }
        publicationRepository.deleteById(id);
    }

    @Override
    public PublicationResponse getPublicationResponseById(UUID id) {
        PublicationEntity publication = publicationRepository.findById(id).orElseThrow(PublicationNotFoundException::new);
        List<String> fileNames;
        if (publication.getType().equals(PublicationType.IMAGE)) {
            fileNames = fileService.findPublicationImagesByPublicationId(publication.getId()).stream()
                    .map(publicationImage -> publicationImage.getId() + "." + publicationImage.getExtension())
                    .collect(Collectors.toList());
        } else {
            fileNames = fileService.findPublicationVideoByPublicationId(publication.getId()).stream()
                    .map(publicationVideo -> publicationVideo.getId() + "." + publicationVideo.getExtension())
                    .collect(Collectors.toList());
        }
        return publicationMapper.getPublicationResponse(publication, fileNames);
    }

    @Override
    public PublicationResponse updatePublicationById(UUID id, UUID accountId, String description) {
        PublicationEntity publication = publicationRepository.findById(id).orElseThrow(PublicationNotFoundException::new);
        if (!publication.getAccountId().equals(accountId)) {
            throw new ForbiddenException("Publication does not belong to this user");
        }
        publication.setDescription(description);
        publication.setUpdatedDate(LocalDateTime.now());
        publicationRepository.update(publication);
        List<String> fileNames;
        if (publication.getType().equals(PublicationType.IMAGE)) {
            fileNames = fileService.findPublicationImagesByPublicationId(publication.getId()).stream()
                    .map(publicationImage -> publicationImage.getId() + "." + publicationImage.getExtension())
                    .collect(Collectors.toList());
        } else {
            fileNames = fileService.findPublicationVideoByPublicationId(publication.getId()).stream()
                    .map(publicationVideo -> publicationVideo.getId() + "." + publicationVideo.getExtension())
                    .collect(Collectors.toList());
        }
        return publicationMapper.getPublicationResponse(publication, fileNames);
    }
}
