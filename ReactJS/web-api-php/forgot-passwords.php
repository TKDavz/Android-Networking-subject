<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/forgot-password.php
    // method POST

    $data = json_decode(file_get_contents("php://input"));
    $email = $data->email;

    $query = "SELECT id, email, password FROM users WHERE email = '$email' AND deleted = 0";

    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $user = $stmt->fetch(PDO::FETCH_ASSOC);
    if ($user) {
        // send eamil otp
        // reset password 123@123a
        // cập nhật password hiện tại thành default password
        $query = "UPDATE users SET password = '123@123a' WHERE email = '$email' AND deleted = 0";
        echo json_encode(
            array(
                "message" => "Mật khẩu đã reset",
                "user" => null
            )
        );
    } else {
        echo json_encode(
            array(
                "message" => "Email không tồn tại",
                "user" => null
            )
        );
    }

?>

