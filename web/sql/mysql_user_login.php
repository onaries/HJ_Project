<?php
	include_once "inc.php";	// DB Information

	if (!$mysqli){
		echo "Error: Unable to connect to MySQL." . PHP_EOL;
		echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
		echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
	}

	$email = $_GET["email"];	// 이메일
	$pwd = $_GET["pwd"];		// 비밀번호
	
	if (empty($email)){
		echo "no email";
	}
	else if (empty($pwd)){
		echo "no pwd";
	}
	else {
		$query = "SELECT * FROM user WHERE user_email = \"".$email."\" and user_pwd = \"".$pwd."\"";
		if($result = $mysqli->query($query)){
			for($rows = array(); $row = $result->fetch_assoc(); $rows[] = $row);
		}
		//echo json_encode($rows);
		echo $result->num_rows;
	}
	

	

	mysqli_close($mysqli);
?>
