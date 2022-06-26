package com.triple.club.api.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.triple.club.api.file.mapper.FileMapper;
import com.triple.club.api.file.vo.FileVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.triple.club.util.DateUtil.dateToString;

@Service
public class FileService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3Client amazonS3Client;
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper, AmazonS3Client amazonS3Client){
        this.fileMapper = fileMapper;
        this.amazonS3Client = amazonS3Client;
    }

    public String UploadToS3(String fileName, MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    @Transactional(readOnly = true)
    public FileVO findById(String id){
        return fileMapper.findById(id);
    }

    @Transactional
    public List<FileVO> saveFiles(List<MultipartFile> multipartFiles, String ownerId){
        List<FileVO> fileVOList = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles){
            String originalFilename = multipartFile.getOriginalFilename();
            String contentType = multipartFile.getContentType();

            String now = dateToString(new Date(), "YYYY-MM-dd HH:mm:ss");
            String newFilename = now + "_" + originalFilename;
            String url = UploadToS3(newFilename, multipartFile);

            FileVO file = FileVO.builder()
                    .contentType(contentType)
                    .ownerId(ownerId)
                    .url(url)
                    .build();
            fileMapper.save(file);
            fileVOList.add(file);
        }

        return fileVOList;
    }

    @Transactional
    public int updateById(FileVO fileVO){
        return fileMapper.updateById(fileVO);
    }

    @Transactional
    public int deleteById(String id){
        return fileMapper.deleteById(id);
    }
}
