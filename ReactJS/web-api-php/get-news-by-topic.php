<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

try {
    include_once 'connection.php';

    // http://127.0.0.1:8686/get-news-by-topic.php?topic_id=1
    // lấy id của topic từ query string
    $topic_id = $_GET['topic_id'];

    $query = "SELECT id, name FROM topics WHERE id = $topic_id ";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $topic = $stmt->fetch(PDO::FETCH_ASSOC);

    // lấy danh sách các news từ database
    $query = "SELECT id, title, content FROM news WHERE topic_id = $topic_id AND deleted = 0";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $news = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode(
        array(
            "message" => "Danh sách các news theo topic {$topic['name']}",
            "news" => $news
        )
    );
} catch (Exception $e) {
    echo json_encode(
        array(
            "message" => "Lỗi: " . $e->getMessage(),
            "news" => null
        )
    );
}

?>