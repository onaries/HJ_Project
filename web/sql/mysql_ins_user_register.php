<?php
	include_once "inc.php";	// DB Information

	if (!$mysqli){
		echo "Error: Unable to connect to MySQL." . PHP_EOL;
		echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
		echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
	}

	$email = $_GET["email"];	// 이메일
	$pwd = $_GET["pwd"];		// 비밀번호
	$name = $_GET["name"];		// 이름
	$phone = $_GET["phone"];	// 전화번호
	$gcm = $_GET["gcm"];		// GCM REG ID
	
	if (empty($email)){
		echo "no email";
	}
	else if (empty($pwd)){
		echo "no pwd";
	}
	else if (empty($name)){
		echo "no name";
	}
	else if (empty($phone)){
		echo "no phone";
	}
	else if (empty($gcm)){
		echo "no gcm";
	}
	else {
		$query = "INSERT INTO user (user_email, user_pwd, user_name, user_phone, user_gcm) VALUES (\"".$email."\",\"".$pwd."\",\"".$name."\",\"".$phone."\",\"".$gcm."\")";
		if($result = $mysqli->query($query)){
			for($rows = array(); $row = $result->fetch_assoc(); $rows[] = $row);
		}
		echo json_encode($rows);

	}

	mysqli_close($mysqli);
?>
