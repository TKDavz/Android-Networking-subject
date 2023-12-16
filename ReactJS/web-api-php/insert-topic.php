<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/insert-topic.php

    try {
        $data = json_decode(file_get_contents("php://input"));
        $name = $data->name;
        $description = $data->description;

        $query = "INSERT INTO topics(name, description, created_at) VALUES ('$name', '$description', now())";

        $stmt = $dbConn->prepare($query);
        $stmt->execute();

        echo json_encode(
            array(
                "status" => true,
                "message" => "Thêm chủ đề thành công"
            )
        );
    } catch (\Throwable $th) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Thêm chủ đề thất bại"
            )
        );
    }

?>