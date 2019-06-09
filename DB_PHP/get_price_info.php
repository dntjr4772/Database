<?php

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET,POST,PUT');
header('Access-Control-Allow-Headers: X-Requested-With,Content-Type');
$json_data = json_decode(file_get_contents('php://input'), TRUE);

try {
    $hostname="mysql:host=dbajou.c3ikqiisottu.ap-northeast-2.rds.amazonaws.com;port=3306;dbname=db_project;charset=utf8";
    $username="root";
    $password="12341234";

    $carmodel =$json_data['model'];
    $carcol =$json_data['carcol'];

    $db = new PDO($hostname, $username, $password);
    $db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $query = "SELECT distinct distance.company,distance.price as distance_price,times.price as times_price FROM db_project.distance_price as distance , db_project.times_price as times, car where car.model=? and car.carcol=? and distance.id=times.id and distance.type=car.type;";
    $stmt = $db->prepare($query);
    $stmt->execute(array($carmodel,$carcol));
    $result = $stmt->fetchAll(PDO::FETCH_NUM);
    echo json_encode($result,JSON_UNESCAPED_UNICODE);
} catch
(PDOException $e) {
    echo $e->getMessage();
}