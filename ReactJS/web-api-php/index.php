
<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    // đọc dữ liệu từ query string
    // http://127.0.0.1:8686/web-api-php/index.php?a=1&b=2&c=1
    $a = $_GET['a'];
    $b = $_GET['b'];
    $c = $_GET['c'];

    $delta = $b * $b - 4 * $a * $c;
    if ($delta < 0) {
        echo json_encode(
            array(
            "message" => "Phương trình vô nghiệm"
            )
        );
        return;
    }

    if ($delta == 0) {
        $x = -$b / (2 * $a);
        echo json_encode(
            array(
            "message" => "Phương trình có nghiệm kép x = $x"
            )
        );
        return;
    }


    $x1 = (-$b + sqrt($delta)) / (2 * $a);
    $x2 = (-$b - sqrt($delta)) / (2 * $a);

    echo json_encode(
        array(
        "message" => "Phương trình có 2 nghiệm phân biệt x1 = $x1, x2 = $x2"
        )
    );

?>