package com.landleaf.ibsaas.web.web.controller.knight;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.vo.FileVO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@Api("file处理类")
@RequestMapping("/knight/")
public class FileController extends BasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Value("${web.localPath}")
    private String picPath;
    @Value("${web.picUrl}")
    private String picUrl;

//    /**
//     * 文件上传
//     * @param entity
//     * @return
//     */
//    @PostMapping("file/upload")
//    public Response upload(FileVO entity) {
//        MultipartFile[] files = entity.getFile();
//        List<String> imgs = new ArrayList<>();
//        for (int i = 0; i < files.length; i++) {
//            LOGGER.info("FileController --->upload file Name :{} ",files[i].getOriginalFilename());
//            String fileName = files[i].getOriginalFilename();
//            fileName = UUID.randomUUID().toString().replaceAll("-","")+"-"+fileName;
//            File dest = new File(picPath +fileName);
//            try {
//                files[i].transferTo(dest);
//                imgs.add(picUrl +fileName);
//           } catch (Exception e) {
//                LOGGER.error("文件上传失败：{}",e.getMessage());
//                return returnValidateError("文件上传失败");
//            }
//        }
//        LOGGER.info("FileController --->upload imgs :{} ", JSONObject.toJSONString(imgs));
//        return returnSuccess(imgs);
//    }


}
