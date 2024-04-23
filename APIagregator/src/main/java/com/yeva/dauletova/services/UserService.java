package com.yeva.dauletova.services;

import com.yeva.dauletova.models.EmailConfirmationToken;
import com.yeva.dauletova.models.User;
import com.yeva.dauletova.repositories.EmailConfirmationTokenRepository;
import com.yeva.dauletova.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.apache.tomcat.util.buf.Ascii;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {
    private EmailSenderService emailSenderService;
    private final BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom(15);
    private final Charset ascii = Charset.forName("US-ASCII");
    private EmailConfirmationTokenRepository tokenRepository;
    private UserRepository userRepository;
    @Autowired
    public UserService(EmailConfirmationTokenRepository tokenRepository, EmailSenderService emailSenderService, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.emailSenderService = emailSenderService;
        this.userRepository = userRepository;
    }

    public void sendRegistrationConfirmationEmail(User user) throws MessagingException {
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken();
        String tokenValue = new String(Base64.getEncoder().encodeToString(keyGenerator.generateKey()));
        emailConfirmationToken.setToken(tokenValue);
        emailConfirmationToken.setUser(user);
        emailConfirmationToken.setCreatedDate(LocalDateTime.now());
        tokenRepository.save(emailConfirmationToken);
        emailSenderService.sendConfirmationEmail(emailConfirmationToken);

    }
    public boolean verifyUser(String token){
        Optional<EmailConfirmationToken> emailConfirmationToken = tokenRepository.findByToken(token);
        if(emailConfirmationToken.isPresent()){
            EmailConfirmationToken emailConfirmationToken1 = emailConfirmationToken.get();
             User user = emailConfirmationToken1.getUser();
             user.setVerified(true);
             userRepository.save(user);
             tokenRepository.delete(emailConfirmationToken1);
             return true;
        }
        return false;
    }
}
