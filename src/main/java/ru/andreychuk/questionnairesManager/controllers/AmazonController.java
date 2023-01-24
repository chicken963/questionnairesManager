package ru.andreychuk.questionnairesManager.controllers;

import com.amazonaws.util.EC2MetadataUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AmazonController {

    @GetMapping("/geo")
    public ResponseEntity<String> getAwsMetadata() {
        return ResponseEntity.ok(EC2MetadataUtils.getAvailabilityZone());
    }
}
