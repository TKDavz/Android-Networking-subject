<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/get-user-detail.php?user_id=1
    
    // lấy id của user từ query string
    $user_id = $_GET['user_id'];
    

    $query = "SELECT id, email, password, name, address, phone, created_at, role FROM users WHERE id = $user_id";

    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($user) {
        echo json_encode(
            array(
                "status" => true, 
                "message" => "Lấy thông tin user thành công",
                "user" => $user
            )
        );
    } else {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Lấy thông tin user thất bại",
                "user" => null
            )
        );
    }

?>

