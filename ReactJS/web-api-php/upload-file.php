<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// http://127.0.0.1:8686/upload-file.php
include_once 'env.php';

try {
    $currentDirectory = getcwd();
    $uploadDirectory = "/uploads/";
    $fileName = $_FILES['image']['name'];
    $fileTmpName = $_FILES['image']['tmp_name'];
    $uploadPath = $currentDirectory . $uploadDirectory . basename($fileName);

// chỉ cho file có đuôi là jpg, png, jpeg
    $extension = pathinfo($fileName, PATHINFO_EXTENSION);
    $allowedType = array('jpg', 'png', 'jpeg');
    if (!in_array($extension, $allowedType)) {
        echo json_encode(
            array(
                "error" => true,
                "message" => "File type is not allowed",
                "path" => null
            )
        );
        return;
    }

    // upload file
    move_uploaded_file($fileTmpName, $uploadPath);
    echo json_encode(
        array(
            "error" => false,
            "message" => "Upload successful",
            "path" => "$BASE_URL/uploads/" . $fileName
            // "path" => "http://127.0.0.1:8686/uploads/".$fileName
        )
    );
    // lấy ip của máy
    // windows: ipconfig -> ipv4 address của wifi adapter hoặc ethernet adapter
    // 172.16.68.154
    // linux: ifconfig -> inet

} catch (Exception $e) {
    echo json_encode(
        array(
            "error" => true,
            "message" => "Upload failed",
            "path" => null
        )
    );
}