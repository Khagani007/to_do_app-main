package com.khagani.to_do_app.service.impl;


import com.khagani.to_do_app.dto.RegistrationDTO;
import com.khagani.to_do_app.entity.User;
import com.khagani.to_do_app.exceptions.UserNotFoundException;
import com.khagani.to_do_app.mapper.UserMapper;
import com.khagani.to_do_app.repository.UserRepository;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final MailSender mailSender;

    private boolean isSending = false;

    private Queue<SimpleMailMessage> queue = new ConcurrentLinkedQueue<>();

    @Value("${app.registration.base-path}")
    private String baseUrl;


    public void register(RegistrationDTO dto) {
        User user = Optional.of(dto)
                .map(mapper::toEntity)
                .map(repository::save)
                .orElseThrow();
        sendEmail(dto.getUsername(), user.getUuid());
    }

    private void sendEmail(String email, UUID uuid) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        System.out.println(email);
        message.setFrom("info@good.company");
        message.setSubject("Registration Confirmation");
        message.setText(baseUrl + "/registration/confirmation/" + uuid);
        queue.add(message);

    }


    public void confirm(UUID uuid) {
        repository.findByUuid(uuid)
                .ifPresentOrElse(this::activateUser, () -> {
                    throw new UserNotFoundException("");
                });

    }

    protected void activateUser(User user) {
        user.setEnabled(true);
        repository.save(user);
    }

    @Scheduled(fixedDelay = 5000)
    public void sendMail() {
        log.info("starting");
        while (!isSending && !queue.isEmpty()) {
            log.info("found email");
            isSending = true;
            log.info("sending");
            mailSender.send(queue.poll());
            log.info("send 1");
        }
        isSending = false;
        log.info("finishing");
    }
}
