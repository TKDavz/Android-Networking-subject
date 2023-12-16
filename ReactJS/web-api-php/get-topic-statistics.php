<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

try {
    include_once 'connection.php';

    // http://127.0.0.1:8686/get-topic-statistics.php
    // lấy tổng số lượng topic, tổng topic được thêm tháng này

    // lấy tổng số lượng topic
    $query = "SELECT COUNT(id) AS total_topics FROM topics WHERE deleted = 0";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $total_topics = $stmt->fetch(PDO::FETCH_ASSOC);
    $total_topics = $total_topics["total_topics"];

    // lấy tổng số lượng topic được thêm tháng này
    $query = "SELECT COUNT(id) AS total_topics FROM topics WHERE MONTH(created_at) = MONTH(CURRENT_DATE()) AND YEAR(created_at) = YEAR(CURRENT_DATE()) AND deleted = 0";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $total_topics_this_month = $stmt->fetch(PDO::FETCH_ASSOC);
    $total_topics_this_month = $total_topics_this_month["total_topics"];


    //xuẩt kết quả
    echo json_encode(
        array(
            "message" => "Thống kê topic",
            "total_topics" => $total_topics,
            "total_topics_this_month" => $total_topics_this_month,
        )
    );
} catch (Exception $e) {
    echo json_encode(
        array(
            "message" => "Lỗi: " . $e->getMessage(),
            "total_topics" => null,
            "total_topics_this_month" => null,
        )
    );
}
?>