<?php
	$con = mysqli_connect("localhost", "root", "", "");
	
	$statement = mysqli_prepare($con, 'SELECT * FROM easyeat_storelist');
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $id, $store_id, $store_name);
	
	$store = array();
	
	while (mysqli_stmt_fetch($statement)) {
		$store[] = array("store_id" => $store_id, "store_name" => $store_name);
	}
	
	echo json_encode($store);

	mysqli_stmt_close($statement);
	
	mysqli_close($con);
	
?>