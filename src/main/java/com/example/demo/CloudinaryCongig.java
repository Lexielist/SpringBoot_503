package com.example.demo;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import com.sun.org.apache.xpath.internal.operations.String;
import org.aspectj.weaver.patterns.PerSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryCongig {
    private Cloudinary cloudinary;
    @Autowired
    public CloudinaryCongig(@Value("${cloud.key}") String key,
                            @Value("${clound.secret}") String secret,
                            @Value("${cloud.name}") String cloud){
        cloudinary = Singleton.getCloudinary();
        cloudinary.config.cloudName=cloud;
        cloudinary.config,apiSecret=secret;
        cloudinary.config.apiKey=key;

    }
    public Map upload(Object file, Map option){
        try{
            return cloudinary.uploader().upload(file,options);

        } catch (IOException e){
            e.printStackTrace();
            return null;

        }

    }
    public String createUrl(String name, int width, int heigh, String action){
        return cloudinary.url()
                .transformation(new Transformation()
                .width(width).height(heigh)
                .border("2px_solid_black").crop(action))
                .imageTag(name);

    }

}
