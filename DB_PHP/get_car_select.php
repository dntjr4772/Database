<?php

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET,POST,PUT');
header('Access-Control-Allow-Headers: X-Requested-With,Content-Type');
$json_data = json_decode(file_get_contents('php://input'), TRUE);

try {
    $hostname="mysql:host=dbajou.c3ikqiisottu.ap-northeast-2.rds.amazonaws.com;port=3306;dbname=db_project;charset=utf8";
    $username="root";
    $password="12341234";

    $carname =$json_data['carname'];

    $db = new PDO($hostname, $username, $password);
    $db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    //$query = "SELECT rentcar.car_num, rentcar.model,rental_place.address,company.name,rentcar.distance_driven FROM rental_place, car,company,rentcar WHERE car.model= ? AND car.model=rentcar.model AND rentcar.company=company.id AND rental_place.name=rentcar.location";
    $query = "SELECT rentcar.car_num,rentcar.model,rentcar.location,company.name,rentcar.distance_driven,rentcar.type FROM db_project.rentcar as rentcar NATURAL JOIN db_project.company as company where rentcar.model=?";
    $stmt = $db->prepare($query);
    $stmt->execute(array($carname));
    $result = $stmt->fetchAll(PDO::FETCH_NUM);
    echo json_encode($result,JSON_UNESCAPED_UNICODE);
} catch
(PDOException $e) {
    echo $e->getMessage();
}

