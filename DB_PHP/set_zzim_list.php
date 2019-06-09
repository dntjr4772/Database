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
        $model=$json_data['model'];
        $query="Create view WishList as SELECT company.name,rentcar.model,rentcar.location,rentcar.car_num,rentcar.type,rentcar.distance_driven FROM db_project.rentcar as rentcar NATURAL JOIN db_project.company as company where rentcar.model=?;";
        $stmt->execute(array($model));
        $result = $stmt->fetchAll(PDO::FETCH_NUM);
        echo 1;
    }else{
        $model=$json_data['model'];
        $location=$json_data['location'];
        $num=$json_data['num'];
        $type=$json_data['type'];
        $distance=$json_data['distance'];

        $query = "Insert into WishList values(0,K5,매탄권선역,12허4855,중형,9920);";
        $stmt = $db->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_NUM);
        echo json_encode($result,JSON_UNESCAPED_UNICODE);
    }

} catch
(PDOException $e) {
    echo $e->getMessage();
}
