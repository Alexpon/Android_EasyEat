<?php
	$con = mysqli_connect("localhost", "root", "atms62x07ship", "project");
	
	$person = $_POST["person"];

	$statement = mysqli_prepare($con, 'SELECT * FROM store_a_database WHERE person = ? AND isok = 2');
	mysqli_stmt_bind_param($statement, "s", $person);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $id, $person, $meal, $money, $number, $isok);
	
	$menu = array();
	
	while (mysqli_stmt_fetch($statement)) {
		$menu[] = array("store_name" => "早安!美芝城", "date" => "20151207", "money" => $money);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	$statement = mysqli_prepare($con, 'SELECT * FROM store_b_database WHERE person = ? AND isok = 2');
	mysqli_stmt_bind_param($statement, "s", $person);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $id, $person, $meal, $money, $number, $isok);
	
	
	while (mysqli_stmt_fetch($statement)) {
		$menu[] = array("store_name" => "阿婆蛋餅", "date" => "20151207", "money" => $money);
	}
	
	echo json_encode($menu);

	mysqli_stmt_close($statement);
	
	mysqli_close($con);
	
?>