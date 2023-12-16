<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    try {
    include_once 'connection.php';

    // http://127.0.0.1:8686/get-news-statistics.php?user_id=1
    // lấy id của user từ query string
    $user_id = $_GET['user_id'];

    //thống kê tổng news, number of your news, number of news this month
    $query = "SELECT COUNT(*) AS total_news FROM news";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $total_news = $stmt->fetch(PDO::FETCH_ASSOC);
    $total_news = $total_news["total_news"];

    $query = "SELECT COUNT(*) AS total_news FROM news WHERE user_id = $user_id";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $total_your_news = $stmt->fetch(PDO::FETCH_ASSOC);
    $total_your_news = $total_your_news["total_news"];

    $query = "SELECT COUNT(*) AS total_news FROM news WHERE user_id = $user_id AND MONTH(created_at) = MONTH(CURRENT_DATE())";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $total_news_this_month = $stmt->fetch(PDO::FETCH_ASSOC);
    $total_news_this_month = $total_news_this_month["total_news"];

    //xuẩt kết quả
    echo json_encode(
        array(
            "message" => "Thống kê tin tức",
            "total_news" => $total_news,
            "total_your_news" => $total_your_news,
            "total_news_this_month" => $total_news_this_month
        )
    );
} catch (Exception $e) {
    echo json_encode(
        array(
            "message" => "Lỗi: " . $e->getMessage(),
            "total_news" => null,
            "total_your_news" => null,
            "total_news_this_month" => null
        )
    );
}
