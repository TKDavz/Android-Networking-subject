<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: PUT");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    include_once 'connection.php';

    try {
            // http://127.0.0.1:8686/update-news.php?id=1
    // lấy id của news cần update
    $news_id = $_GET['id'];

    // lấy dữ liệu từ request
    $data = json_decode(file_get_contents("php://input"));

    // lấy dữ liệu từ request
    $title = $data->title;
    $content = $data->content;
    $image = $data->image;
    $topic_id = $data->topic_id;
    $user_id = $data->user_id;

    // cập nhật dữ liệu vào database
    $query = "UPDATE news SET title = '$title', content = '$content', image = '$image', topic_id = '$topic_id', user_id = '$user_id' WHERE id = $news_id" ;
    $stmt = $dbConn->prepare($query);
    $stmt->execute();

        echo json_encode(
            array(
                "status" => true, 
                "message" => "Cập nhật news thành công"
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => $e->getMessage()
            )
        );
    }


    

?>