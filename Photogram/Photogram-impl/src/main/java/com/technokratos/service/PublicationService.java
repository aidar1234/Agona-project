package com.technokratos.service;

import com.technokratos.dto.response.PublicationResponse;
import com.technokratos.model.jooq.enums.PublicationType;
import com.technokratos.model.jooq.tables.pojos.PublicationEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicationService {

    PublicationResponse getPublicationResponseById(UUID id);

    PublicationResponse updatePublicationById(UUID id, UUID accountId, String description);

    UUID createImagePublicationByAccountId(UUID accountId, String description, List<MultipartFile> files);

    UUID createVideoPublicationByAccountId(UUID accountId, String description, MultipartFile file);

    Optional<PublicationEntity> findPublicationById(UUID id);

    List<PublicationResponse> getPublicationsByAccountId(UUID id);

    void deletePublicationById(UUID id, UUID accountId);
}
