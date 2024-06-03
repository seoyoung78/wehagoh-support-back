package com.duzon.lulu.service.MSC.common.model;

import com.duzon.lulu.service.MSC.util.HealthSessionContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper=false)
public class FileInfo extends HealthSessionContext {
    private MultipartFile file;
    private String transactionId;
    private String authorization;
}
