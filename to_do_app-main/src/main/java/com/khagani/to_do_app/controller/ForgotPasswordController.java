package com.khagani.to_do_app.controller;


import com.khagani.to_do_app.dto.ResetPasswordDto;
import com.khagani.to_do_app.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final EmailService emailService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@Valid @RequestParam String email) {
        emailService.forgotPassword(email);
    }

    @PutMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestParam ResetPasswordDto resetPasswordDto) {
      emailService.resetPassword(resetPasswordDto);
    }
}
