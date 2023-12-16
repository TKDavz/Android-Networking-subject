<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // http://127.0.0.1:8686/get-news-detail.php?news_id=1
    // lấy id của news từ query string
    $news_id = $_GET['news_id'];

    // lấy danh sách các news từ database
    $query = "SELECT n.id as id, n.title as title, n.content as content, n.created_at as created_at, n.image as image,
     u.name as author_name, t.name as topic_name
        FROM news n
        INNER JOIN users u ON n.user_id = u.id
        INNER JOIN topics t ON n.topic_id = t.id
        WHERE n.id = $news_id";

    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $news = $stmt->fetch(PDO::FETCH_ASSOC);

    echo json_encode(
        array(
            "message" => "Chi tiết tin",
            "news" => $news
        )
    );
?>