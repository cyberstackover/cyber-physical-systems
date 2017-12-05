package com.cyber.api.controllers.v1.api;

import com.cyber.api.hadoop.fs.HDFSClient;
import com.cyber.api.utils.JsonResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(value = "/v1")
@RestController
public class HDFSManagerController {

    private static final Logger LOG = LoggerFactory.getLogger(HDFSManagerController.class);

    private final String DEST_TEMP_FILEUPLOAD = "/var/temp/";

    @Autowired
    private HDFSClient hdfsClient;

    @RequestMapping(value = "hdfs-manager", method = RequestMethod.GET)
    public JsonResponse index(@RequestParam(value = "path", defaultValue = "/") String path) throws IOException, InterruptedException {
        List<Map<String, Object>> status = hdfsClient.getAllFilesAndDirectory(path);
        return JsonResponse.render(HttpStatus.OK, status);
    }

    @RequestMapping(value = "hdfs-manager/upload", method = RequestMethod.POST)
    public JsonResponse upload(@RequestParam("path") String path,
            @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {

                String destTemp = DEST_TEMP_FILEUPLOAD + file.getOriginalFilename();

                File dirDst = new File(DEST_TEMP_FILEUPLOAD);
                if (!dirDst.isDirectory()) {
                    dirDst.mkdirs();
                }

                File tempFile = new File(destTemp);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream
                        = new BufferedOutputStream(new FileOutputStream(tempFile));

                stream.write(bytes);
                stream.close();

                hdfsClient.copyFromLocal(destTemp, path);

                tempFile.delete();
                return JsonResponse.render(HttpStatus.OK, "ok");
            } catch (Exception e) {
                return JsonResponse.render(HttpStatus.OK, e);
            }
        } else {
            return JsonResponse.render(HttpStatus.OK, "ok");
        }
    }

    @RequestMapping(value = "hdfs-manager/mkdir", method = RequestMethod.POST)
    public JsonResponse mkdir(@RequestBody Map dir) throws IOException, InterruptedException {
        String directory = dir.get("directory").toString();
        if (!directory.isEmpty()) {
            hdfsClient.mkdir(directory);
        }
        return JsonResponse.render(HttpStatus.OK, "ok");
    }

    @RequestMapping(value = "hdfs-manager", method = RequestMethod.DELETE)
    public JsonResponse delete(@RequestParam(value = "path") String path) {
        if (!path.equalsIgnoreCase("/")) {
            hdfsClient.deleteFile(path);
        }
        return JsonResponse.render(HttpStatus.OK, "ok");
    }

}
