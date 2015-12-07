
<?php	
		include("conf.php");
		header("Content-Type:text/html; charset=utf-8");
		$result = mysql_query("SELECT * FROM `Store_A_Database` ");//menu
		$store_id = '001';
		$json = array();
		while($row = mysql_fetch_row($result) ){
			$str = $row[2];
			$Arr = str_split($str, 2);
			$result2 = mysql_query("SELECT * FROM `EasyEat_StoreMenu` WHERE store_id = '$store_id'");//menu
			$s4 = '';
			$count = 0;
			while($row2 = mysql_fetch_row($result2) ){
				if($Arr[$count] > 0){
					$s1 =  $row2[4] ;
					$s2 = '*';
					$s3 = $Arr[$count];
					$s4 = $s4.$s1.$s2.$s3;
				}
				$count = $count + 1;
				
			}
			
			
				$json[] = array("person" => $row[1], "menu" => $s4, "money" => $row[3], "number" => $row[4], "isok" => $row[5]);

			
		}
		print json_encode($json);
?>
