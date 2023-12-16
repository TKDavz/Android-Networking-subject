<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: DELETE");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/delete-topic.php?topic_id=1

    try {
        // lấy id của topic từ query string
        $topic_id = $_GET['topic_id'];

        // kiểm tra xem có tồn tại 1 news nào đó có topic_id = $topic_id hay không
        $query = "SELECT id FROM news WHERE topic_id = $topic_id";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $news = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if ($news) {
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "Không thể xóa chủ đề này vì có tin tức thuộc chủ đề này"
                )
            );
            return;
        }

        // xóa topic có id = $topic_id
        $query = "UPDATE topics SET deleted = 1, deleted_at = now() WHERE id = $topic_id";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();

        echo json_encode(
            array(
                "status" => true,
                "message" => "Xóa chủ đề thành công"
            )
        );
    } catch (\Throwable $th) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Xóa chủ đề thất bại"
            )
        );
    }

?>