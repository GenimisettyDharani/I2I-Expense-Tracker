import React, { useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import UserService from '../../../services/userService';
import { encryptData } from '../../../services/encryptionService'; // Importing the encryption function

function ChangePassword({ email }) {
    const { register, handleSubmit, watch, reset, formState } = useForm();
    const password = useRef({});
    password.current = watch('newPassword', "");
    const [isLoading, setIsLoading] = useState(false);

    const onSubmit = async (data) => {
        setIsLoading(true);
        try {
            // Encrypt the new password
            const encryptedNewPassword = encryptData(data.newPassword);

            await UserService.settingsResetPassword(email, data.currentPassword, encryptedNewPassword).then(
                (response) => {
                    if (response.data.status === "SUCCESS") {
                        toast.success(response.data.response);
                        reset({ currentPassword: "", newPassword: "", cpassword: "" });
                        return;
                    }
                },
                (error) => {
                    error.response ?
                        toast.error(error.response.data.response) :
                        toast.error("Failed to change password: Try again later!");
                }
            );
        } catch (error) {
            toast.error("An error occurred while updating the password. Please try again later.");
            console.error("Password change error: ", error);
        }
        setIsLoading(false);
    }

    return (
        <form className="auth-form t-form" onSubmit={handleSubmit(onSubmit)}>
            <div className='input-box'>
                <label>Current Password</label><br />
                <input
                    type='password'
                    {
                        ...register('currentPassword', {
                            required: 'Current password is required!',
                            minLength: {
                                value: 8,
                                message: "Password must have at least 8 characters"
                            }
                        })
                    }
                />
                {formState.errors.currentPassword && <small>{formState.errors.currentPassword.message}</small>}
            </div>

            <div className='input-box'>
                <label>Password</label><br />
                <input
                    type='password'
                    {
                        ...register('newPassword', {
                            required: 'Password is required!',
                            minLength: {
                                value: 8,
                                message: "Password must have at least 8 characters"
                            }
                        })
                    }
                />
                {formState.errors.newPassword && <small>{formState.errors.newPassword.message}</small>}
            </div>

            <div className='input-box'>
                <label>Confirm Password</label><br />
                <input
                    type='password'
                    {
                        ...register('cpassword', {
                            required: 'Confirm password is required!',
                            minLength: {
                                value: 8,
                                message: "Password must have at least 8 characters"
                            },
                            validate: cpass => cpass === password.current || "Passwords do not match!"
                        })
                    }
                />
                {formState.errors.cpassword && <small>{formState.errors.cpassword.message}</small>}
            </div>

            <div className='t-btn input-box'>
                <input type='submit' value={isLoading ? "Updating..." : 'Update password'}
                    className={isLoading ? "button button-fill loading" : "button button-fill"} />
            </div>
        </form>
    );
}

export default ChangePassword;
