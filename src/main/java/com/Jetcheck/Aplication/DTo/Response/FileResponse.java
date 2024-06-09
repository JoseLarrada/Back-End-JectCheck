package com.Jetcheck.Aplication.DTo.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {
    private String key;
    private String url;
    private String fileName;
}
