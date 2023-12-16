<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: PUT");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/update-topic.php

    try {
        $data = json_decode(file_get_contents("php://input"));
        $id = $data->id;
        $name = $data->name;
        $description = $data->description;

        $query = "UPDATE topics SET name = '$name', description = '$description' WHERE id = $id";

        $stmt = $dbConn->prepare($query);
        $stmt->execute();

        echo json_encode(
            array(
                "status" => true,
                "message" => "Cập nhật chủ đề thành công"
            )
        );
    } catch (\Throwable $th) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Cập nhật chủ đề thất bại"
            )
        );
    }
?>