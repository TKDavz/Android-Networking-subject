<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

try {
    include_once 'connection.php';

    // http://127.0.0.1:8686/get-topics-sta.php

    // thống kê số lượng news mối topic
    $query = "SELECT topics.id, topics.name, COUNT(news.id) AS total_news FROM topics LEFT JOIN news ON topics.id = news.topic_id WHERE topics.deleted = 0  GROUP BY topics.id";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $topics = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // thống kê top 3 topic có nhiều news nhất
    $query = "SELECT topics.id, topics.name, COUNT(news.id) AS total_news FROM topics LEFT JOIN news ON topics.id = news.topic_id WHERE topics.deleted = 0 GROUP BY topics.id ORDER BY total_news DESC LIMIT 3";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $top3_topics = $stmt->fetchAll(PDO::FETCH_ASSOC);


    //xuẩt kết quả
    echo json_encode(
        array(
            "message" => "Thống kê số lượng news mối topic",
            "newsOfTopics" => $topics,
            "top3Topics" => $top3_topics,
        )
    );
} catch (Exception $e) {
    echo json_encode(
        array(
            "message" => "Lỗi: " . $e->getMessage(),
            "topics" => null,
            "top3Topics" => null,
        )
    );
}
?>