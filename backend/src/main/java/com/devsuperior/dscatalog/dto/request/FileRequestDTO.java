package com.devsuperior.dscatalog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3964221668011293562L;

    private MultipartFile file;
}
