package com.technokratos.api.v1;

import io.swagger.annotations.Api;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/files")
@Api(tags = "Files")
public interface FileApi {

    @GetMapping("/image/{name}")
    ResponseEntity<Resource> getImage(@PathVariable String name);

    @GetMapping("/video/{name}")
    ResponseEntity<Resource> getVideo(@PathVariable String name);
}
