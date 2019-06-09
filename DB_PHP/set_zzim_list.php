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
        $model2=$json_data['model'];
        $carcol2=$json_data['carcol'];

        $query2="Create view WishList as SELECT distinct company,model,location,car_num,type,distance_driven FROM db_project.rentcar as rentcar NATURAL JOIN db_project.company as company where rentcar.model='".$model2."' and rentcar.rentcarcol='".$carcol2."'";

        $stmt2 = $db->prepare($query2);
        $stmt2->execute(array($model2,$carcol2));
        $result = $stmt2->fetchAll(PDO::FETCH_NUM);
        echo $query2;
    }else{
        $query0="DROP VIEW db_project.WishList;";
        $stmt0 = $db->prepare($query0);
        $stmt0->execute();
        $result = $stmt0->fetchAll(PDO::FETCH_NUM);

        $model1=$json_data['model'];
        $carcol1=$json_data['carcol'];

        $query1="Create view WishList as SELECT distinct company,model,location,car_num,type,distance_driven FROM db_project.rentcar as rentcar NATURAL JOIN db_project.company as company where rentcar.model='".$model1."' and rentcar.rentcarcol='".$carcol1."'";
        $stmt1 = $db->prepare($query1);
        $stmt1->execute();
        $result = $stmt1->fetchAll(PDO::FETCH_NUM);
        echo $query1;
    }

} catch
(PDOException $e) {
    echo $e->getMessage();
}
