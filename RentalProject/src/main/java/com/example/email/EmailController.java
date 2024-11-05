package com.example.email;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class EmailController {
    @Autowired
    private EmailService emailService;

    private static int OTP;

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtpMailToUser(@RequestParam("userEmail") String userEmail) {
        OTP = this.emailService.generateOtp();
        return new ResponseEntity<>(
                Map.of("message", this.emailService.sendOtpMail(userEmail, OTP)), HttpStatus.OK);

    }

    @GetMapping("/get-otp")
    public ResponseEntity<Map<String, Integer>> getOtp() {
        return new ResponseEntity<>(Map.of("otp", OTP), HttpStatus.OK);
    }

    @PostMapping("/send-message")
    public ResponseEntity<Map<String, String>> sendMessageMailToUser(@RequestParam("userEmail") String userEmail) {
        return new ResponseEntity<>(Map.of("message", this.emailService.sendMessageMailString(userEmail)),
                HttpStatus.OK);

    }
}
