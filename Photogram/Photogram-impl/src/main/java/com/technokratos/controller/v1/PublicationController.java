package com.technokratos.controller.v1;

import com.technokratos.api.v1.PublicationApi;
import com.technokratos.dto.response.PublicationResponse;
import com.technokratos.security.AuthUtil;
import com.technokratos.security.AuthenticationPrinciple;
import com.technokratos.service.PublicationService;
import com.technokratos.validation.validator.file.PublicationImagesValidator;
import com.technokratos.validation.validator.file.PublicationVideoValidator;
import com.technokratos.validation.validator.publication.PublicationDescriptionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PublicationController implements PublicationApi {

    private final PublicationImagesValidator publicationImagesValidator;
    private final PublicationVideoValidator publicationVideoValidator;
    private final PublicationDescriptionValidator publicationDescriptionValidator;
    private final PublicationService publicationService;
    private final AuthUtil authUtil;

    @Override
    public ResponseEntity<PublicationResponse> getPublication(UUID id) {
        PublicationResponse response = publicationService.getPublicationResponseById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public void createImagePublication(String description, List<MultipartFile> files) {
        publicationDescriptionValidator.validate(description);
        publicationImagesValidator.validate(files);

        AuthenticationPrinciple principle = authUtil.getPrincipal();

        publicationService.createImagePublicationByAccountId(principle.getId(), description, files);
    }

    @Override
    public void createVideoPublication(String description, MultipartFile file) {
        publicationDescriptionValidator.validate(description);
        publicationVideoValidator.validate(file);

        AuthenticationPrinciple principle = authUtil.getPrincipal();

        publicationService.createVideoPublicationByAccountId(principle.getId(), description, file);
    }

    @Override
    public ResponseEntity<PublicationResponse> updatePublication(UUID id, String description) {
        publicationDescriptionValidator.validate(description);

        AuthenticationPrinciple principle = authUtil.getPrincipal();

        PublicationResponse response = publicationService.updatePublicationById(id, principle.getId(), description);
        return ResponseEntity.ok(response);
    }

    @Override
    public void deletePublication(UUID id) {
        AuthenticationPrinciple principle = authUtil.getPrincipal();

        publicationService.deletePublicationById(id, principle.getId());
    }
}
