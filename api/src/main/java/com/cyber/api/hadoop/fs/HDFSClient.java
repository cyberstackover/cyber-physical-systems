package com.cyber.api.hadoop.fs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.core.io.DefaultResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDFSClient {

    private static final Logger LOG = LoggerFactory.getLogger(HDFSClient.class);

    protected FileSystem fileSystem;

    protected Configuration config;

    protected UserGroupInformation ugi;

    public HDFSClient(String site, String hdfs, String mapred) throws IOException {
        //ugi = UserGroupInformation.createRemoteUser("hduser");
        final DefaultResourceLoader loader = new DefaultResourceLoader();

        config = new Configuration();
        config.addResource(loader.getResource(site).getInputStream());
        config.addResource(loader.getResource(hdfs).getInputStream());
        config.addResource(loader.getResource(mapred).getInputStream());
        fileSystem = FileSystem.get(config);

    }

    public List<Map<String, Object>> getAllFilesAndDirectory(String path) throws IOException, InterruptedException {
        
        List<Map<String, Object>> fileList = new ArrayList<>();
        FileStatus[] fileStatus = fileSystem.listStatus(new Path(path));
        
        for (FileStatus fileStat : fileStatus) {
            Map<String, Object> file = new HashMap<>();
       
            file.put("name", fileStat.getPath().getName());
            file.put("modification_time", fileStat.getModificationTime());
            file.put("owner", fileStat.getOwner());
            file.put("block_size", fileStat.getBlockSize());
            file.put("group", fileStat.getGroup());
            file.put("permission", fileStat.getPermission().toString());
            file.put("size", fileStat.getLen());
            
            fileList.add(file);
        }
        return fileList;
    }

    public void addFile(String source, String dest) throws IOException {

        String filename = source.substring(source.lastIndexOf('/') + 1, source.length());

        if (dest.charAt(dest.length() - 1) != '/') {
            dest = dest + "/" + filename;
        } else {
            dest = dest + filename;
        }

        Path path = new Path(dest);
        
        if (fileSystem.exists(path)) {
            LOG.info("File " + dest + " already exists");
            return;
        }
        
        FSDataOutputStream out = fileSystem.create(path);
        InputStream in = new BufferedInputStream(new FileInputStream(new File(source)));

        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = in.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }

        in.close();
        out.close();
    }

    public void copyFromLocal(String source, String dest) throws IOException {

        Path srcPath = new Path(source);
        Path dstPath = new Path(dest);

        if (!(fileSystem.exists(dstPath))) {
            LOG.info("No such destination " + dstPath);
            return;
        }
        
        String filename = source.substring(source.lastIndexOf('/') + 1, source.length());
        
        try {
            fileSystem.copyFromLocalFile(srcPath, dstPath);
            LOG.info("File " + filename + "copied to " + dest);
        } catch (Exception e) {
            LOG.error("Exception caught! :" + e);
        }
    }

    public void renameFile(String fromthis, String tothis) throws IOException {

        Path fromPath = new Path(fromthis);
        Path toPath = new Path(tothis);

        if (!(fileSystem.exists(fromPath))) {
            LOG.info("No such destination " + fromPath);
            return;
        }

        if (fileSystem.exists(toPath)) {
            LOG.info("Already exists! " + toPath);
            return;
        }

        try {
            boolean isRenamed = fileSystem.rename(fromPath, toPath);
            if (isRenamed) {
                LOG.info("Renamed from " + fromthis + "to " + tothis);
            }
        } catch (Exception e) {
            LOG.error("Exception :" + e);
            System.exit(1);
        }
    }

    public void mkdir(String dir) throws IOException {
        Path path = new Path(dir);
        if (fileSystem.exists(path)) {
            LOG.info("Dir " + dir + " already exists!");
            return;
        }
        fileSystem.mkdirs(path);
    }

    public void deleteFile(String file) {
        try {
            fileSystem.delete(new Path(file), true);
        } catch (IOException ex) {
            LOG.error("file not found");
        }
    }

}
