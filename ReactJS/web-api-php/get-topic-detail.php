<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/get-topic-detail.php?topic_id=1
    // lấy id của news từ query string
    $topic_id = $_GET['topic_id'];

    // lấy danh sách các news từ database
    $query = "SELECT id, name, description FROM topics WHERE id = $topic_id AND deleted = 0";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $topic = $stmt->fetch(PDO::FETCH_ASSOC);

    echo json_encode(
        array(
            "message" => "Chi tiết topic",
            "topic" => $topic
        )
    );
?>