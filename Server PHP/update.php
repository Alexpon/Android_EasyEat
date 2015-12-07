<?php
include("conf.php");
$postnum = $_POST['num'];
$sql = "UPDATE `store_a_database` SET `isok`= 1 WHERE number = $postnum";

if(mysql_query($sql)){
   echo 'yes';
    }
    else{
    echo 'no';
    }

?>