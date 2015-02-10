<?php

$response = array();
 
require_once __DIR__ . '/db_connect.php';
 
$db = new DB_CONNECT();
 

if (isset($_POST['nickname']) && isset($_POST['password'])) {
	$nickname = $_POST['nickname'];
	$password = $_POST['password'];
    $result = mysql_query("SELECT userId, nickname, passw FROM med.users WHERE nickname = '$nickname' AND passw ='$password'");
 
    if (!empty($result)) {
		
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $user = array();
            $user["userId"] = $result["userId"];
            $user["nickname"] = $result["nickname"];
			$user["password"] = $result["passw"];
            $response["success"] = 1;
 
            $response["user"] = array();
 
            array_push($response["user"], $user);

            echo json_encode($response);
        } else {
            $response["success"] = 2;
			$response["message"] = "User is not found";	

            echo json_encode($response);
        }
    } else {

        $response["success"] =3;
        $response["message"] = "Empty result";

        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
	
    echo json_encode($response);
}
?>