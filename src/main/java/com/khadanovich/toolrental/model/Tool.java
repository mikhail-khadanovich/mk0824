package com.khadanovich.toolrental.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tool {

    private String toolCode;
    private ToolType toolType;
    private String brand;

}
