<?php
include("conf.php");
$store_id = $_POST['Store_id'];;
$store_name = $_POST['Store_name'];;
$food_id = $_POST['food_id'];
$food_name = $_POST['food_name'];
$money = $_POST['money'];
//$postlat = $_POST['abc'];


//$sql = "INSERT INTO `easyeat_storemenu`( `store_id`, `store_name`, `food_id`, `food_name`, `money`) VALUES ($store_id, $store_name , $food_id , $food_name , $money)";
$sql = "INSERT INTO `easyeat_storemenu`( `store_id`, `store_name`, `food_id`, `food_name`, `money`) VALUES ('$store_id', '$store_name' , '$food_id' , '$food_name' , $money)";


if(mysql_query($sql)){
   echo 'yes';
    }
    else{
    echo 'no';
    }

?>