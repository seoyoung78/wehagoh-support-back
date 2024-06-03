package com.duzon.lulu.service.MSC.MSC_030000.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Getter
@Setter
public class File {
    private String file_path_id; // 파일 경로 아이디
    private Integer file_srl_no; // 파일 시리얼 번호(순번)
    private String file_nm; // 파일 명칭
    private String file_extn_nm; // 파일 확장 명
}
