package com.egran.egran_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Integer id;
    private String url;
    private LocalDateTime timestamp;
    private Double lat;
    private Double lng;

}
