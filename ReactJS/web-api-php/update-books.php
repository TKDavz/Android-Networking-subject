<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: PUT");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    include_once 'connection.php';

    try {
            // http:///update-news.php?id=1
    // lấy id của book cần update
    $book_id = $_GET['id'];

    // lấy dữ liệu từ request
    $data = json_decode(file_get_contents("php://input"));

    // lấy giá
    $price = $data->price;


    // Câu 3 validate 
    if (!(is_numeric($price))) throw new Exception("'price' phải là số nguyên dương và đồng thời phải có giá trị từ 100 -> 1000", 1);
    if ($price >= 100 && $price <= 1000) throw new Exception("'price' phải là số nguyên dương và đồng thời phải có giá trị từ 100 -> 1000",1);
    

    // cập nhật dữ liệu vào database
    $query = "UPDATE books SET price = $price WHERE id = $book_id" ;
    $stmt = $dbConn->prepare($query);
    $stmt->execute();

        echo json_encode(
            array(
                "status" => true, 
                "message" => "Cập nhật thành công",
                "e" => null
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Cập nhật thất bại",
                "e" => $e->getMessage()
            )
        );
    }

?>