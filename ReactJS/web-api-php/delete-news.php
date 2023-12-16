<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: DELETE");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once 'connection.php';

// http://127.0.0.1:8686/delete-news.php?id=1
try {
    $news_id = $_GET['id'];


    $query = "UPDATE news SET deleted = 1, deleted_at = now() WHERE id = $news_id";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $topics = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // nếu xóa thành công thì trả về thông báo thành công, còn thất bại thì trả về thông báo thất bại

        echo json_encode(
            array(
                "status" => true, 
                "message" => "Xóa news thành công"
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