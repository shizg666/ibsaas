package com.landleaf.ibsaas.web.web.vo;

import org.springframework.web.multipart.MultipartFile;

public class FileVO {
//    private String name;
////    private String des;
    private MultipartFile[] file;

    public MultipartFile[] getFile() {
        return file;
    }

    public void setFile(MultipartFile[] file) {
        this.file = file;
    }
}
