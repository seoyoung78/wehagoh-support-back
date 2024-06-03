package com.duzon.lulu.service.MSC.common.controller;

import com.duzon.clinichelper.objectstorage.application.facade.ObjectStorageFacade;
import com.duzon.clinichelper.objectstorage.model.dto.UploadDto;
import com.duzon.clinichelper.objectstorage.model.dto.UploadSignResponseDto;
import com.duzon.common.model.LuluResult;
import com.duzon.lulu.annotation.HealthRestController;
import com.duzon.lulu.service.MSC.common.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@HealthRestController("/file")
public class FileController {
    @Autowired
    private IFileService iFileService;
    private final ObjectStorageFacade objectStorageFacade;

    public FileController(ObjectStorageFacade objectStorageFacade) {
        this.objectStorageFacade = objectStorageFacade;
    }

    @PostMapping(value = "/getSigniture", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Object getSigniture(@ModelAttribute UploadDto file) throws Exception {
        LuluResult result = new LuluResult();
        UploadSignResponseDto signResponseDto = objectStorageFacade.getUploadSignature(file);
        result.setResultCode(200);
        result.setResultData(signResponseDto.getResultList());
        return result;
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Object uploadFile(@ModelAttribute UploadDto file
            , @RequestHeader("Transaction-Id") String transactionId) throws Exception {
        LuluResult result = new LuluResult();
        result.setResultCode(200);
        String fileName = objectStorageFacade.saveFile(transactionId, file);
        result.setResultData(fileName);
        return result;
    }
}
