<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/insert-news.php
    // lấy dữ liệu từ body của request
    try {
        $data = json_decode(file_get_contents("php://input"));

        $title = $data->title;     // Use -> to access object properties
        $content = $data->content; 
        $image = $data->image;
        $topic_id = $data->topic_id; 
        $user_id = $data->user_id;   
        // thêm mới tin vào database
        $query = "INSERT INTO news(title, content, image, topic_id, user_id, created_at, created_by) 
        VALUES ('$title', '$content', '$image', $topic_id, $user_id, now(), $user_id)";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();

        echo json_encode(
            array(
                "status" => true,
                "message" => "Thêm mới tin thành công"
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Thêm mới tin thất bại: " . $e->getMessage()
            )
        );
    }


?>