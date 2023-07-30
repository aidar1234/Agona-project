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
public class AccountImageValidator implements Validator<MultipartFile> {

    public static List<String> validImageFormats = List.of("image/png", "image/jpeg", "image/jpg");

    private final Integer maxImageSize;
    private final Tika tika;

    public AccountImageValidator(@Value("${file.max-account-image-size}") Integer maxImageSize,
                                      @Autowired Tika tika) {
        this.maxImageSize = maxImageSize;
        this.tika = tika;
    }

    @Override
    public void validate(MultipartFile file) throws RuntimeException {
        if (file == null || file.isEmpty() || file.getSize() == 0) {
            throw new BadRequestException("File is empty");
        }
        if (getSizeInMB(file.getSize()) > maxImageSize) {
            throw new BadRequestException("The image size cannot exceed " + maxImageSize + " MB");
        }
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

    private long getSizeInMB(long size) {
        return size / (1024 * 1024);
    }
}
