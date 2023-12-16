<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/register.php
    // method POST
    try {
        $data = json_decode(file_get_contents("php://input"));
        $email = $data->email;
        $password = $data->password;
        $confirm_password = $data->confirm_password;
        $name = $data->name;
        $phone = $data->phone;
        $address = $data->address;
        $role = $data->role;

        if ($password != $confirm_password) {
            echo json_encode(
                array(
                    "status" => false, // false
                    "message" => "Mật khẩu không khớp!",
                    "user" => null
                )
            );
            return;
        }

        // validate
        // kiểm tra email đã tồn tại hay chưa
        $query = "SELECT id FROM users WHERE email = '$email'";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $user = $stmt->fetch(PDO::FETCH_ASSOC);
        if ($user) {
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "Email đã tồn tại",
                    "user" => null
                )
            );
            return;
        } else {
            $query = "INSERT INTO users (email, password, name, phone, address, role) 
            VALUES ('$email', '$password', '$name', '$phone', '$address', '$role')";

            $stmt = $dbConn->prepare($query);
            $stmt->execute();
            echo json_encode(
                array(
                    "status" => true,
                    "message" => "Đăng ký thành công",
                    "user" => $user
                )
            );
        }

    } catch (\Throwable $th) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Đăng ký thất bại",
                "user" => null
            )
        );
    }

?>

