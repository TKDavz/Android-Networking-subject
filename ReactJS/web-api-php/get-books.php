<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

try {
    include_once 'connection.php';

    // http://ip:port/get-books.php
    // lấy danh sách các news từ database

    $data = json_decode(file_get_contents("php://input"));
    $genre = $data->genre;
    $author = $data->author;

    $query = "SELECT id, title, price, genre, author, published 
                FROM books
                WHERE genre = '$genre' AND author = '$author' 
                ORDER BY price DESC";

    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $books = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode(
        array(
            "message" => "Danh sách books theo thứ tự giảm giá tiền",
            "status" => true,
            "books" => $books
        )
    );

} catch (Exception $e) {
    echo json_encode(
        array(
            "message" => "Lỗi: " . $e->getMessage(),
            "status" => false,
            "books" => null
        )
    );
}
?>