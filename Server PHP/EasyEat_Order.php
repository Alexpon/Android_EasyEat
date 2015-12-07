<?php	
	$con = mysqli_connect("localhost", "root", "atms62x07ship", "project");
	$store = $_POST["store"];
	$person = $_POST["person"];
	$order = $_POST["order"];

	if($store == '001'){
		$database = 'store_a_database';
	}
	if($store == '002'){
		$database = 'store_b_database';
	}
	
	
	$statement = mysqli_prepare($con, "SELECT * FROM EasyEat_StoreMenu WHERE store_id = $store");
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $id, $store_id, $store_name, $food_id, $food_name, $money);
	$count = 0;
	$price = 0;
	$Arr = str_split($order, 2);
	while (mysqli_stmt_fetch($statement)) {
		$price = $price + $money*$Arr[$count];
		$count = $count + 1;
	}
	
	
	$statement = mysqli_prepare($con, "SELECT * FROM $database");
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $id, $person1, $meal, $money, $number, $isok);
	$num = 0;
	$numnow = 0;
	while (mysqli_stmt_fetch($statement)) {
	/*
		if($isok == 0){
			$num = $number;
		}*/
		$num++;
		if($isok == 1){
			$numnow = $number;
		}
	}
	
	$num++;
	$output = array();
	$output[] = array("num" => $num, "now" => $numnow);

	echo json_encode($output);
	
	$statement = mysqli_prepare($con, "INSERT INTO $database (person ,meal ,money ,number ,isok) VALUES ('$person', '$order', '$price', '$num', '0' )");
	mysqli_stmt_execute($statement);
	mysqli_stmt_close($statement);
	

?>