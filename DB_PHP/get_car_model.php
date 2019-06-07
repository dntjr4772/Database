<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET,POST,PUT');
header('Access-Control-Allow-Headers: X-Requested-With,Content-Type');
$json_data = json_decode(file_get_contents('php://input'), TRUE);


#echo json_encode($total, JSON_UNESCAPED_UNICODE);
try {
    $hostname="mysql:host=dbajou.c3ikqiisottu.ap-northeast-2.rds.amazonaws.com;port=3306;dbname=db_project;charset=utf8";
    $username="root";
    $password="12341234";
    $cartype =$json_data['car_type'];
    $people= $json_data['people'];
    $db = new PDO($hostname, $username, $password);
    $db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $query = "SELECT car.model,car.company FROM car WHERE type= ? AND passengers=?";

    $stmt = $db->prepare($query);
    $stmt->execute(array($cartype, $people));
    $result = $stmt->fetchAll(PDO::FETCH_NUM);

    echo json_encode($result,JSON_UNESCAPED_UNICODE);
} catch
(PDOException $e) {
    echo $e->getMessage();
}