package tech.pm.edu.domain.service.impl;

import org.springframework.stereotype.Service;
import tech.pm.edu.domain.service.PasswordEncryptionService;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

@Service
public class PasswordEncryptionDefault implements PasswordEncryptionService {

  @Override
  public String encryptPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(password.getBytes(StandardCharsets.UTF_8));
    byte[] digest = md.digest();
    return DatatypeConverter.printHexBinary(digest).toLowerCase(Locale.ROOT);
  }


}
