package com.sn.socialnetworkapp.payload.attachment;

public record CreateAttachmentFile(byte[] content,
                                   String originalName,
                                   String contentType) {
}