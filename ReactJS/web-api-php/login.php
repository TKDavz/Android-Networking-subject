<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/login.php
    // method POST

    $data = json_decode(file_get_contents("php://input"));
    $email = $data->email;
    $password = $data->password;

    $query = "SELECT id, email, password, name, phone, created_at, role FROM users WHERE email = '$email' AND password = '$password' AND deleted = 0";

    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($user) {
        echo json_encode(
            array(
                "status" => true, 
                "message" => "Đăng nhập thành công",
                "user" => $user
            )
        );
    } else {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Đăng nhập thất bại",
                "user" => null
            )
        );
    }

?>

