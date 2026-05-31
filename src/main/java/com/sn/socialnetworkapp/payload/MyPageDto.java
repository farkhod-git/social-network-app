package com.sn.socialnetworkapp.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageDto<T> {
    List<T> content;
    int page;
    int size;
    int numberOfElements;
    long totalElements;
    int totalPages;
    boolean last;

    public static <T> MyPageDto<T> empty() {
        return MyPageDto.<T>builder()
                .content(Collections.emptyList())
                .build();
    }
}
