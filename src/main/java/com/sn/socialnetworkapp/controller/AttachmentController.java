package com.sn.socialnetworkapp.controller;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.attachment.AttachmentDto;
import com.sn.socialnetworkapp.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable UUID id,
                                             @RequestParam(required = false, defaultValue = "true") boolean attachmentDownload) {
        return attachmentService.download(id, attachmentDownload);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponseDto<AttachmentDto> upload(@RequestParam("file") MultipartFile file) {
        return attachmentService.upload(file);
    }

}
