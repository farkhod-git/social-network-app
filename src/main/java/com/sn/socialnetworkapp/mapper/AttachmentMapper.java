package com.sn.socialnetworkapp.mapper;

import com.sn.socialnetworkapp.entity.Attachment;
import com.sn.socialnetworkapp.payload.attachment.AttachmentDto;
import com.sn.socialnetworkapp.payload.attachment.CreateAttachmentFile;
import org.apache.commons.io.FilenameUtils;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    AttachmentDto toDto(Attachment attachment);
}
