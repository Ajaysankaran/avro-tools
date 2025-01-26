package com.tool.avro_tools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class PageRequest {

    private int pageNo;

    private int pageSize;
}
