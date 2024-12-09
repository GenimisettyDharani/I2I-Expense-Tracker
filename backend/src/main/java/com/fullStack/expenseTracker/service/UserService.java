package com.fullStack.expenseTracker.service;

import com.fullStack.expenseTracker.security.EncryptionUtil;
import com.fullStack.expenseTracker.exception.UserNotFoundException;
import com.fullStack.expenseTracker.exception.UserServiceLogicException;
import com.fullStack.expenseTracker.dto.ApiResponseDto;
import com.fullStack.expenseTracker.dto.ResetPasswordRequestDto;
import com.fullStack.expenseTracker.model.User;
import com.fullStack.expenseTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<ApiResponseDto<?>> updateUserPassword(ResetPasswordRequestDto resetPasswordDto)
            throws UserNotFoundException, UserServiceLogicException {

        if (userRepository.existsByEmail(resetPasswordDto.getEmail())) {
            try {
                User user = userRepository.findByEmail(resetPasswordDto.getEmail())
                        .orElseThrow(() -> new UserNotFoundException("User not found with email " + resetPasswordDto.getEmail()));

                // Decrypt the new password to perform the update
                String decryptedNewPassword = encryptionUtil.decryptData(resetPasswordDto.getNewPassword());

                if (!resetPasswordDto.getCurrentPassword().isEmpty()) {
                    if (!isPasswordValid(resetPasswordDto.getCurrentPassword(), user.getPassword())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(
                                ApiResponseStatus.FAILED,
                                HttpStatus.BAD_REQUEST,
                                "Reset password not successful: current password is incorrect!!"
                        ));
                    }
                }

                // Perform password update with the decrypted password
                user.setPassword(encryptionUtil.encryptData(decryptedNewPassword));
                userRepository.save(user);

                return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(
                        ApiResponseStatus.SUCCESS,
                        HttpStatus.CREATED,
                        "Reset successful: Password has been successfully reset!"
                ));
            } catch (Exception e) {
                log.error("Updating user password failed: {}", e.getMessage());
                throw new UserServiceLogicException("Failed to update the password: Try again later!");
            }
        }
        
        throw new UserNotFoundException("User not found with email " + resetPasswordDto.getEmail());
    }

    private boolean isPasswordValid(String rawPassword, String encodedPassword) {
        // Implement password matching logic here, for instance using BCrypt
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
