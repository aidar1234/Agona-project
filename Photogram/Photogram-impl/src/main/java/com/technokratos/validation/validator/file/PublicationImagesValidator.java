package com.technokratos.validation.validator.file;

import com.technokratos.exception.BadRequestException;
import com.technokratos.exception.InternalServerError;
import com.technokratos.validation.validator.Validator;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class PublicationImagesValidator implements Validator<List<MultipartFile>> {

    public static List<String> validImageFormats = List.of("image/png", "image/jpeg", "image/jpg");

    private final Integer maxImageSize;
    private final Integer maxImagesByRequest;
    private final Tika tika;

    public PublicationImagesValidator(@Value("${file.max-publication-image-size}") Integer maxImageSize,
                                      @Value("${file.max-publication-images-by-request}") Integer maxImagesByRequest,
                                      @Autowired Tika tika) {
        this.maxImageSize = maxImageSize;
        this.maxImagesByRequest = maxImagesByRequest;
        this.tika = tika;
    }

    @Override
    public void validate(List<MultipartFile> files) {
        if (files == null || files.isEmpty() || files.get(0).getSize() == 0) {
            throw new BadRequestException("Files list is empty");
        }
        if (files.size() > maxImagesByRequest) {
            throw new BadRequestException("The count of images cannot exceed 10");
        }
        for (MultipartFile file : files) {
            if (getSizeInMB(file.getSize()) > maxImageSize) {
                throw new BadRequestException("The image size cannot exceed " + maxImageSize + " MB");
            }
        }
        for (MultipartFile file : files) {
            try {
                String format = tika.detect(file.getInputStream());
                boolean validFormat = false;
                for (String type : validImageFormats) {
                    if (type.equals(format)) {
                        validFormat = true;
                        break;
                    }
                }
                if (!validFormat) {
                    throw new BadRequestException("Invalid image format");
                }
            } catch (IOException e) {
                throw new InternalServerError();
            }
        }
    }

    private long getSizeInMB(long size) {
        return size / (1024 * 1024);
    }
}
