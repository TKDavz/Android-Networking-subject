<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

try {
    include_once 'connection.php';

    // http://127.0.0.1:8686/search-news-by-keyword.php?keyword=abc
    // lấy từ khóa tìm kiếm từ query string
    $keyword = $_GET['keyword'];

    // lấy danh sách các news từ database
    $query = "SELECT id, title, content FROM news WHERE title LIKE '%$keyword%' AND deleted = 0";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $news = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode(
        array(
            "message" => "Danh sách các news tìm kiếm được với từ khóa '$keyword'",
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