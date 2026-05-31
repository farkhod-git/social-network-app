package com.sn.socialnetworkapp.service;

import com.sn.socialnetworkapp.entity.Attachment;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.attachment.AttachmentDto;
import com.sn.socialnetworkapp.payload.attachment.CreateAttachmentFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {
    ResponseEntity<Resource> download(UUID id, boolean attachmentDownload);

    ApiResponseDto<AttachmentDto> upload(MultipartFile file);

    Attachment create(byte[] content, String originalName) throws IOException;
}
