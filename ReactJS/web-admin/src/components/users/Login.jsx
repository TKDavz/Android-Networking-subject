import React, { useState } from "react";
import AxiosInstance from "../helper/AxiosInstance";
import './Login.css';

const Login = (props) => {
    const { user, setUser } = props;

    const roleArr = [
        {
            id: 1,
            name: "Admin"
        }, {
            id: 2,
            name: "User"
        }
    ]

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showRegister, setShowRegister] = useState(false);

    if (user) {
        window.location.href = "/";
    }

    const handleLogin = async () => {
        try {
            if (email === '' || password === '') {
                alert("Email and password are required");
            }
            // sử dụng regex để kiểm tra email
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                throw new Error("Email is invalid");
            }

            // Gửi request lên máy chủ để đăng nhập
            const body = { email, password };
            const response = await AxiosInstance().post("/login.php", body);
            console.log(response);
            console.log(response.message);
            if (response.user.role === "admin") {
                setUser(response.user);
                alert(response.message);
            } else {
                alert("Bạn không có quyền truy cập");
            }
            // if (response.user !== null) {
            //     // Lưu thông tin người dùng vào localStorage
            //     localStorage.setItem("user", JSON.stringify(response.user));
            //     // Chuyển hướng người dùng đến trang chủ
            //     window.location.href = "/";
            // }
        } catch (error) {
            console.log("Failed to login", error);
        }
    }

    const [role, setRole] = useState(roleArr[0]);



    return (
        <div className="body" style={{ width: "100%", height: "100%", display: "flex", alignItems: "center", justifyContent: "center" }}>
            <section className="w-100 p-4 d-flex justify-content-center pb-4">
                <div action="" style={{ width: 26 + 'em', marginTop: !showRegister && 100 + "px" }}>
                    {/* <!-- Pills navs --> */}
                    <ul className="nav nav-pills nav-justified mb-3" id="ex1" role="tablist">
                        <li className="nav-item" role="presentation" onClick={() => { setShowRegister(false) }}>
                            <a className={"nav-link " + (!showRegister && "active")} id="tab-login" data-mdb-toggle="pill" href="#pills-login" role="tab"
                                aria-controls="pills-login" aria-selected={!showRegister}>Login</a>
                        </li>
                        <li className="nav-item" role="presentation" onClick={() => { setShowRegister(true) }}>
                            <a className={"nav-link " + (showRegister && "active")} id="tab-register" data-mdb-toggle="pill" href="#pills-register" role="tab"
                                aria-controls="pills-register" aria-selected={showRegister}>Register</a>
                        </li>
                    </ul>
                    {/* <!-- Pills navs --> */}

                    {/* <!-- Pills content --> */}
                    <div className="tab-content">
                        <div className="tab-pane fade show active" id="pills-login" role="tabpanel" aria-labelledby="tab-login" style={!showRegister ? { display: "block" } : { display: "none" }}>
                            <form>
                                {/* <div className="text-center mb-3">
                                <p>Sign in with:</p>
                                <button type="button" className="btn btn-link btn-floating mx-1">
                                    <i className="fab fa-facebook-f"></i>
                                </button>

                                <button type="button" className="btn btn-link btn-floating mx-1">
                                    <i className="fab fa-google"></i>
                                </button>

                                <button type="button" className="btn btn-link btn-floating mx-1">
                                    <i className="fab fa-twitter"></i>
                                </button>

                                <button type="button" className="btn btn-link btn-floating mx-1">
                                    <i className="fab fa-github"></i>
                                </button>
                            </div>

                            <p className="text-center">or:</p> */}

                                {/* <!-- Email input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="loginName">Email</label>
                                    <input type="email" id="loginName" className="form-control" value={email} onChange={(event) => setEmail(event.target.value)} />
                                </div>

                                {/* <!-- Password input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="loginPassword">Password</label>
                                    <input type="password" id="loginPassword" className="form-control" value={password} onChange={(event) => setPassword(event.target.value)} />
                                </div>

                                {/* <!-- 2 column grid layout --> */}
                                <div className="row mb-4">
                                    <div className="col-md-6 d-flex justify-content-center">
                                        {/* <!-- Checkbox --> */}
                                        <div className="form-check mb-3 mb-md-0">
                                            <input className="form-check-input" type="checkbox" value="" id="loginCheck" defaultChecked />
                                            <label className="form-check-label" htmlFor="loginCheck"> Remember me </label>
                                        </div>
                                    </div>

                                    <div className="col-md-6 d-flex justify-content-center">
                                        {/* <!-- Simple link --> */}
                                        <a href="#!">Forgot password?</a>
                                    </div>
                                </div>

                                {/* <!-- Submit button --> */}
                                <button type="button" className="btn btn-primary btn-block mb-4" onClick={handleLogin}>Sign in</button>

                                {/* <!-- Register buttons --> */}
                                <div className="text-center">
                                    <p>Not a member? <a href="#!" onClick={() => { setShowRegister(true) }}>Register</a></p>
                                </div>
                            </form>
                        </div>

                        <div className="tab-pane fade show active" id="pills-register" role="tabpanel" aria-labelledby="tab-register" style={showRegister ? { display: "block" } : { display: "none" }}>
                            <form>
                                {/*     <div className="text-center mb-3">
                                <p>Sign up with:</p>
                                <button type="button" className="btn btn-link btn-floating mx-1">
                                    <i className="fab fa-facebook-f"></i>
                                </button>

                                <button type="button" className="btn btn-link btn-floating mx-1">
                                    <i className="fab fa-google"></i>
                                </button>

                                <button type="button" className="btn btn-link btn-floating mx-1">
                                    <i className="fab fa-twitter"></i>
                                </button>

                                <button type="button" className="btn btn-link btn-floating mx-1">
                                    <i className="fab fa-github"></i>
                                </button>
                            </div>

                            <p className="text-center">or:</p> */}

                                {/* <!-- Name input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="registerName">Name</label>
                                    <input type="text" id="registerName" className="form-control" />

                                </div>

                                {/* <!-- Email input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="registerEmail">Email</label>
                                    <input type="email" id="registerEmail" className="form-control" />

                                </div>

                                {/* <!-- Password input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="registerPassword">Password</label>
                                    <input type="password" id="registerPassword" className="form-control" />

                                </div>

                                {/* <!-- Repeat Password input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="registerRepeatPassword">Repeat password</label>
                                    <input type="password" id="registerRepeatPassword" className="form-control" />

                                </div>

                                {/* <!-- Phone input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="registerPhone">Phone</label>
                                    <input type="text" id="registerPhone" className="form-control" />
                                </div>

                                {/* <!-- Address input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="registerAddress">Address</label>
                                    <input type="text" id="registerAddress" className="form-control" />
                                </div>

                                {/* <!-- Role input --> */}
                                <div className="form-outline mb-4">
                                    <label className="form-label" htmlFor="registerRole">Role</label>
                                    {/* <input type="text" id="registerRole" className="form-control" value={"admin"} /> */}
                                    <select
                                        name="role"
                                        id="registerRole"
                                        className="form-control"
                                        value={role.id}
                                        onChange={(e) => { setRole({ id: e.target.value, name: e.target.name }) }}>
                                        {roleArr.map((role, index) => (
                                            <option key={role.id} value={role.id}>
                                                {role.name}
                                            </option>
                                        ))}
                                    </select>
                                </div>

                                {/* <!-- Checkbox --> */}
                                <div className="form-check d-flex justify-content-center mb-4">
                                    <input className="form-check-input me-2" type="checkbox" value="" id="registerCheck" defaultChecked
                                        aria-describedby="registerCheckHelpText" />
                                    <label className="form-check-label" htmlFor="registerCheck">
                                        I have read and agree to the terms
                                    </label>
                                </div>

                                {/* <!-- Submit button --> */}
                                <button type="submit" className="btn btn-primary btn-block mb-4">Sign up</button>
                            </form>
                        </div>
                    </div>
                    {/* <!-- Pills content --> */}
                </div>
            </section>
        </div>
    )
}

export default Login

// "id": 1,
// "email": "email1@gmail.com",
// "password": "123456",
// "name": "admin",
// "phone": "0123456789",
// "created_at": "2021-04-01 00:00:00",
// "role": "admin"

// chuyển nó thành khai báo java class
// class User {
//     int id;
//     String email;
//     String password;
//     String name;
//     String phone;
//     String created_at;
//     String role;
// }
