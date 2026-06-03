package com.sn.socialnetworkapp.mapper;

import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.payload.auth.RegisterDto;
import com.sn.socialnetworkapp.payload.user.ProfileDto;
import com.sn.socialnetworkapp.payload.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AttachmentMapper.class)
public interface UserMapper {
    UserDto toDto(User user);

    ProfileDto toProfileDto(User user);

    List<UserDto> toDtoList(List<User> content);
}
