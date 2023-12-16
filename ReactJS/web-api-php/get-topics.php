<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/get-topics.php
    // lấy danh sách các topic từ database
    $query = "SELECT id, name, description FROM topics WHERE deleted = 0";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $topics = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode(
        array(
            "message" => "Danh sách các topic",
            "topics" => $topics
        )
    );
?>