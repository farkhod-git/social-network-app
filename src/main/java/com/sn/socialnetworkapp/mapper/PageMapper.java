package com.sn.socialnetworkapp.mapper;

import com.sn.socialnetworkapp.payload.MyPageDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

    default <F, T> MyPageDto<T> toCustomPageDto(Page<F> page) {
        return MyPageDto
                .<T>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .last(page.isLast())
                .build();
    }

}
