package com.sn.socialnetworkapp.service;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.messageread.MessageReadDto;

import java.util.List;

public interface MessageReadService {

    ApiResponseDto<List<MessageReadDto>> reads(Long id);

    void read(List<Long> messageIds);
}
