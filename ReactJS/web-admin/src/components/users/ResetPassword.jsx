import React, { useState, useEffect } from "react";
import AxiosInstance from "../helper/AxiosInstance";
import { useSearchParams } from "react-router-dom";
import swal from 'sweetalert';

const ResetPassword = () => {
    const [getParams, setParams] = useSearchParams();
    console.log(getParams.get('email'), getParams.get('token'));

    const [email, setEmail] = useState(getParams.get('email'));
    const [token, setToken] = useState(getParams.get('token'));

    const [isValid, setIsValid] = useState(false);

    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    useEffect(() => {
        const checkResetPassword = async () => {
            try {
                if (!email || !token) throw new Error("Invalid URL");

                const body = { email, token };
                const res = await AxiosInstance.post("/check-reset-password.php", body);
                console.log(res);
                setIsValid(res.status);
            } catch (error) {
                console.log(error);
            }
        };
        checkResetPassword();
    }, []);


    const handleResetPassword = async () => {
        try {
            if (!email || !token) throw new Error("Invalid URL");

            const body = { email, token, password, confirm_password: confirmPassword };
            const res = await AxiosInstance.post("/reset-password.php", body);
            console.log(res);
            if (res.status === true) {
                swal("Reset password thành công", {
                    icon: "success",
                });
            }
        } catch (error) {
            console.log(error);
        }
    }

    // kiểm tra email và token có hợp lệ hay không
    if (!email || !token ) {
        return (
            <div className="container">
                <h1>Reset Password</h1>
                <p >
                    Invalid token or email
                </p>
            </div>
        );
    } else {

        return (
            <div>
                <h1>Reset Password</h1>
                <form>
                    {/* tạo input */}
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input type="password" className="form-control"
                            value={password} onChange={(e) => setPassword(e.target)} />
                    </div>
                    {/* tạo input */}
                    <div className="form-group">
                        <label htmlFor="confirmPassword">Confirm Password</label>
                        <input type="password" className="form-control"
                            value={confirmPassword} onChange={(e) => setConfirmPassword(e.target)} />
                    </div>
                    {/* tạo button */}
                    <button className="btn btn-primary">Reset Password</button>
                </form>
            </div>
        );
    }
}

export default ResetPassword;