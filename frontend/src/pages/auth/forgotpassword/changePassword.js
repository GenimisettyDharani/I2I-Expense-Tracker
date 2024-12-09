import { encryptData } from '../../services/encryptionService';
import AuthService from '../../services/auth.service';

// Function to handle password reset
const handleResetPassword = async (email, newPassword) => {
    try {
        // Encrypt the new password before sending it to the server
        const encryptedPassword = encryptData(newPassword);

        // Call the AuthService to reset the password
        const response = await AuthService.resetPassword(email, encryptedPassword);

        // Handle the response accordingly (Success feedback, etc.)
        console.log('Password reset successfully:', response);
    } catch (error) {
        // Handle errors appropriately
        console.error('Error resetting password:', error);
        // Optionally provide user feedback with a more user-friendly message
    }
};

// Export the reset password handler for use in other parts of the application
export { handleResetPassword };
