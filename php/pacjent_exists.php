<?php

$response = array();
 
require_once __DIR__ . '/db_connect.php';
 
$db = new DB_CONNECT();
 

if (isset($_POST['userId'])) {
	$userId = $_POST['userId'];

    $result = mysql_query("select p.idPacjenta, p.imie, p.nazwisko, p.pesel, p.telefon, p.eMail, p.adres, p.plec from pacjent as p join users on p.userId = '$userId'");
 
    if (!empty($result)) {
		
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $user = array();
            $user["imie"] = $result["imie"];
			$user["idPacjenta"] = $result["idPacjenta"];
			$user["nazwisko"] = $result["nazwisko"];
			$user["pesel"] = $result["pesel"];
			$user["telefon"] = $result["telefon"];
			$user["eMail"] = $result["eMail"];
			$user["adres"] = $result["adres"];
			$user["plec"] = $result["plec"];
		     
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