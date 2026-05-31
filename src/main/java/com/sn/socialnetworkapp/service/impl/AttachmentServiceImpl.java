package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.entity.Attachment;
import com.sn.socialnetworkapp.exceptions.MyBadRequestException;
import com.sn.socialnetworkapp.exceptions.MyNotFoundException;
import com.sn.socialnetworkapp.mapper.AttachmentMapper;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.attachment.AttachmentDto;
import com.sn.socialnetworkapp.payload.attachment.CreateAttachmentFile;
import com.sn.socialnetworkapp.repository.AttachmentRepository;
import com.sn.socialnetworkapp.service.AttachmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private static final Tika TIKA = new Tika();

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    @Value("${file.base-path}")
    private String basePath;

    @Override
    public ResponseEntity<Resource> download(UUID id, boolean attachmentDownload) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Attachment not found"));

        ContentDisposition cd = (attachmentDownload ? ContentDisposition.attachment()
                : ContentDisposition.inline())
                .filename(attachment.getOriginalName())
                .build();

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, cd.toString())
                .body(new FileSystemResource(Path.of(basePath, attachment.getPath())));
    }

    @Override
    public ApiResponseDto<AttachmentDto> upload(MultipartFile file) {
        try {
            final byte[] content = file.getBytes();

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.matches("[a-zA-Z0-9-._]+")) {
                throw new MyBadRequestException("Invalid file name");
            }

            Attachment attachment = create(content, originalFilename);

            return ApiResponseDto.success(attachmentMapper.toDto(attachment));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Attachment create(byte[] content, String originalName) throws IOException {
        final String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(originalName);
        final String path = DateTimeFormatter.ofPattern("yy/MM/dd/").format(LocalDate.now()) + filename;
        final String contentType = TIKA.detect(new ByteArrayInputStream(content));

        Attachment attachment = new Attachment();
        attachment.setContentType(contentType);
        attachment.setOriginalName(originalName);
        attachment.setFilename(filename);
        attachment.setPath(path);
        attachment.setSize(content.length);
        attachmentRepository.save(attachment);

        // copy file
        Path full = Path.of(basePath, attachment.getPath());
        Files.createDirectories(full.getParent());
        Files.copy(new ByteArrayInputStream(content), full);

        return attachment;
    }

}
