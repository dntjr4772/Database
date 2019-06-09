<?php

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET,POST,PUT');
header('Access-Control-Allow-Headers: X-Requested-With,Content-Type');
$json_data = json_decode(file_get_contents('php://input'), TRUE);

try {
    $hostname="mysql:host=dbajou.c3ikqiisottu.ap-northeast-2.rds.amazonaws.com;port=3306;dbname=db_project;charset=utf8";
    $username="root";
    $password="12341234";


    $db = new PDO($hostname, $username, $password);
    $db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    //$query = "SELECT rentcar.car_num, rentcar.model,rental_place.address,company.name,rentcar.distance_driven FROM rental_place, car,company,rentcar WHERE car.model= ? AND car.model=rentcar.model AND rentcar.company=company.id AND rental_place.name=rentcar.location";

    $cc ='VIEW';
    $query = "SHOW FULL TABLES IN db_project WHERE TABLE_TYPE LIKE ?";
    $stmt = $db->prepare($query);
    $stmt->execute(array($cc));
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_NUM);
    if($result==null){
        echo -1;
    }else{
        $query = "SELECT * FROM WishList;";
        $stmt = $db->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_NUM);
        echo json_encode($result,JSON_UNESCAPED_UNICODE);
    }

} catch
(PDOException $e) {
    echo $e->getMessage();
}
