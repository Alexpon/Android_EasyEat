<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
<head>
<style>
h1{font-family:"Comic Sans MS";
width:200px;}
h2{font-family:"Comic Sans MS";
width:200px;}
h3{font-family:"Comic Sans MS";
width:200px;}
</style>
</head>
<body align="center" >

<table border="1" align="center" style="border: 5px; background-color: red;">
		<tr><td><h2>印出</h2></td></tr>
		<table border="1" align="center" style="border: 5px  dashed rgb(109, 2, 107); background-color: rgb(255, 255, 255);">
		<tr>
		<td><h3>編號</h3></td><td><h3>名稱</h3></td><td><h3>價格</h3></td>
		</tr>
<?php	

		include("conf.php");
		$string = '001,0028,01000000020103';
		$str_sec = explode(",",$string);
		$storeid = $str_sec[0] ;//店家
		if($storeid == '001'){
			$database = 'store_a_database';
			$store_id = '001';
		}
		if($storeid == '002'){
			$database = 'store_b_database';
			$store_id = '002';
		}
		$storeid = 'easyeat_storemenu';
		$result = mysql_query("SELECT * FROM `easyeat_storemenu` WHERE store_id = '$store_id' ");//menu
		
		$personid = $str_sec[1] ;//人
		$str = $str_sec[2] ;
		$count = 0;
//		$str='0200070004';
		$price = 0;
		$Arr = str_split($str, 2);
		while($row = mysql_fetch_row($result) ){
			print "<tr><td><h3>".$row[1]."</h3></td><td><h3>".$row[2]."</h3></td><td><h3>".$row[3]."</h3></td><td><h3>".$row[4]."</h3></td><td><h3>".$row[5]."</h3></td></tr> ";
			
			if($Arr[$count] > 0){
				print $row[4] ;
				print '*';
				print $Arr[$count];
				echo '<br>';
			}
			
			$price = $price + $row[5]*$Arr[$count];
			$count = $count + 1;
			
		}
		print '<tr></tr>';
		print $price;
		echo '<br>';
		
		$result2 = mysql_query("SELECT * FROM `$database` ");
		$num = 0;
		$numnow = 0;
		while($row2 = mysql_fetch_row($result2) ){
			if($row2[5] == 0){
				$num = $row2[4];
			}
		}
		$num++;
		$result3 = mysql_query("SELECT * FROM `$database` ");
		while($row3 = mysql_fetch_row($result3) ){
			if($row3[5] == 1){
				$numnow = $row3[4];
			}
		}
		echo '<br>';
		echo 'now number is : ';
		echo $numnow;
		echo '<br>';
		 
		
		$isok = 0;
		$sql = "INSERT INTO  `project`.`$database` (`id` ,`person` ,`meal` ,`money` ,`number` ,`isok`) VALUES ('0' ,  '$personid',  '$str',  '$price',  '$num',  '0')";
		if(mysql_query($sql))
        {
                echo 'yes';
        }
        else
        {
                echo 'no';
        }
		echo '<br>';
		echo 'your number is : ';
		echo $num;
		echo '<br>';

?>

  </body>
</html>