package tech.pm.edu.domain.service;

import java.security.NoSuchAlgorithmException;

public interface PasswordEncryptionService {

  String encryptPassword(String password) throws NoSuchAlgorithmException;

}
