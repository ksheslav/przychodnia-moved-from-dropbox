
<?php

$response = array();
 

if (isset($_POST['password']) && isset($_POST['nickname'])){
 
    $password = $_POST['password'];
	$nickname = $_POST['nickname'];
    
 
    
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO users(passw, nickname) VALUES('$password', '$nickname')");
 
    
    if ($result) {
    
        $response["success"] = 1;
        $response["message"] = "User successfully created.";
 
        echo json_encode($response);
    } else {
  
        $response["success"] = 2;
        $response["message"] = "User exists.";
 

        echo json_encode($response);
    }
} else {

    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    echo json_encode($response);
}
?>