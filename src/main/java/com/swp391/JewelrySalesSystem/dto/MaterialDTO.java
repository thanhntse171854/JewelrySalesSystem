package com.swp391.JewelrySalesSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {
    private Long id;
    private String name;
    private float weight;
}
