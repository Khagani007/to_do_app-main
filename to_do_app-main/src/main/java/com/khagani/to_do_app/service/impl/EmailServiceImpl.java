package com.khagani.to_do_app.service.impl;

import com.khagani.to_do_app.dto.ResetPasswordDto;
import com.khagani.to_do_app.entity.User;
import com.khagani.to_do_app.exceptions.EmailNotFoundException;
import com.khagani.to_do_app.exceptions.UserNotFoundException;
import com.khagani.to_do_app.mapper.UserMapper;
import com.khagani.to_do_app.repository.UserRepository;
import com.khagani.to_do_app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final MailSender mailSender;
    private final UserMapper mapper;
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 5;


    @Override
    public void forgotPassword(String email) {

        Optional<User> userOptional = userRepository.findByUsername(email);

        if (userOptional.isEmpty()) {
            throw new EmailNotFoundException("This email is not registered");
        }

        User user = userOptional.get();
        user.setOtpCode(generateOtp());
        user.setTokenCreationDate(LocalDateTime.now());
        user = userRepository.save(user);

        user.getOtpCode();

        sendEmail(user.getEmail(), user.getOtpCode());


    }


    private void sendEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("khagani1@gmail.com");
        message.setSubject("Password update");
        message.setText(otp);
        mailSender.send(message);

    }


    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findByUsername(resetPasswordDto.getUsername())
                .orElseThrow(()-> new EmailNotFoundException("This email is not registered"));

        LocalDateTime tokenCreationDate = user.getTokenCreationDate();

        if (isOtpExpired(tokenCreationDate)) {
            throw new UserNotFoundException("Otp is expired");
        }

        user.setPassword(resetPasswordDto.getPassword());
        userRepository.save(user);


    }


    private  String generateOtp() {
        Random random = new Random();
        int max = 9999;
        int min = 1000;
        return String.valueOf(random.nextInt(max - min + 1) + min);
    }


    private boolean isOtpExpired(LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}
