<?php
	$con = mysqli_connect("localhost", "root", "", "");
	
	$store = $_POST["store"];

	$statement = mysqli_prepare($con, 'SELECT * FROM EasyEat_StoreMenu WHERE store_name = ?');
	mysqli_stmt_bind_param($statement, "s", $store);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $id, $store_id, $store_name, $food_id, $food_name, $money);
	
	$menu = array();
	
	while (mysqli_stmt_fetch($statement)) {
		$menu[] = array("food_id" => $food_id, "food_name" => $food_name, "money" => $money);
	}
	
	echo json_encode($menu);

	mysqli_stmt_close($statement);
	
	mysqli_close($con);
	
?>