package com.example.honda_dealership.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotorcycleCategoryId implements Serializable {

    private Long motorcycleId;
    private Long categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotorcycleCategoryId that = (MotorcycleCategoryId) o;
        return Objects.equals(motorcycleId, that.motorcycleId) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(motorcycleId, categoryId);
    }
}