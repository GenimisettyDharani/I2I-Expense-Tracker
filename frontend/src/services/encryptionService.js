/**
 * This service provides functionality to encrypt and decrypt sensitive data.
 */

import CryptoJS from 'crypto-js';

// Secret key used for the symmetric encryption
const SECRET_KEY = process.env.SECRET_KEY || 'default_secret_key'; // replace with a more secure method of storing

/**
 * Encrypts the data input by the user to ensure confidentiality.
 * This function uses a symmetric encryption algorithm to encode the data before sending it to the server.
 * It will be called by other services that handle user input.
 * 
 * @param {string} data - The data to be encrypted.
 * @returns {string} - The encrypted data in Base64 format.
 * @throws {Error} - Throws an error if encryption fails.
 */
export const encryptData = (data) => {
    if (typeof data !== 'string') {
        throw new TypeError('Input data must be a string');
    }
    try {
        const encrypted = CryptoJS.AES.encrypt(data, SECRET_KEY).toString();
        return encrypted;
    } catch (error) {
        console.error('Encryption failed', error);
        throw new Error('Could not encrypt data: ' + error.message);
    }
};
