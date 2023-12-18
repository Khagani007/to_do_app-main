package com.khagani.to_do_app.service;

import com.khagani.to_do_app.dto.ResetPasswordDto;

public interface EmailService {


    void forgotPassword(String email);


    void resetPassword(ResetPasswordDto resetPasswordDto);
}
