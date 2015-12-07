<?php

	include("conf.php");
	$postperson = $_POST['person'];
	$result = mysql_query("SELECT * FROM `store_a_database` ");
	$store = array();

	while ($row = mysql_fetch_row($result)) {
		if($row[1] == $postperson and $row[5] == 1){
			$num = $row[4];
			$money = $row[3];
			$store[] = array("num" => $num, "money" => $money);
		}
	
	}
	
	echo json_encode($store);

	
?>