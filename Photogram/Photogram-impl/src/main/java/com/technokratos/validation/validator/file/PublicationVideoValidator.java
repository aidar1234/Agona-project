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
public class PublicationVideoValidator implements Validator<MultipartFile> {

    public static List<String> validVideoFormats = List.of("video/mp4");

    private final Integer maxVideoSize;
    private final Tika tika;

    public PublicationVideoValidator(@Value("${file.max-publication-video-size}") Integer maxVideoSize,
                                     @Autowired Tika tika) {
        this.maxVideoSize = maxVideoSize;
        this.tika = tika;
    }

    @Override
    public void validate(MultipartFile file) throws RuntimeException {
        if (file == null || file.isEmpty() || file.getSize() == 0) {
            throw new BadRequestException("File is empty");
        }
        if (getSizeInMB(file.getSize()) > maxVideoSize) {
            throw new BadRequestException("The video size cannot exceed " + maxVideoSize + " MB");
        }
        try {
            String format = tika.detect(file.getInputStream());
            boolean validFormat = false;
            for (String type : validVideoFormats) {
                if (type.equals(format)) {
                    validFormat = true;
                    break;
                }
            }
            if (!validFormat) {
                throw new BadRequestException("Invalid video format");
            }
        } catch (IOException e) {
            throw new InternalServerError();
        }
    }

    private long getSizeInMB(long size) {
        return size / (1024 * 1024);
    }
}
