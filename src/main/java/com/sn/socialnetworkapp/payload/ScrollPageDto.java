package com.sn.socialnetworkapp.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ScrollPageDto<T> {
    public enum Direction {
        UP,
        DOWN
    }

    List<T> content;
    Direction direction;

    public static <T> ScrollPageDto<T> up(List<T> content) {
        return new ScrollPageDto<>(content, Direction.UP);
    }

    public static <T> ScrollPageDto<T> down(List<T> content) {
        return new ScrollPageDto<>(content, Direction.DOWN);
    }
}
