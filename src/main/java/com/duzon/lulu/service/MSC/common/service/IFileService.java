package com.duzon.lulu.service.MSC.common.service;

import com.duzon.common.model.LuluResult;
import com.duzon.lulu.service.MSC.common.model.FileInfo;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;

public interface IFileService {
    LuluResult fileUploadService(FileInfo fileInfo);
}
