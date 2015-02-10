<?php

$response = array();
 
require_once __DIR__ . '/db_connect.php';
 
$db = new DB_CONNECT();
 

if (isset($_POST['getdoctorlist'])) {
    $result = mysql_query("SELECT lekarzId, imie, nazwisko, specjalizacja FROM med.lekarz");
 
    if (!empty($result)) {
		
        if (mysql_num_rows($result) > 0) {

            $response["lekarz"] = array();

              while ($row = mysql_fetch_array($result)){
              
               $lekarz = array();
                $lekarz["lekarzId"] = $row["lekarzId"];
               $lekarz["imie"] = $row["imie"];
		  	   $lekarz["nazwisko"] = $row["nazwisko"];
                $lekarz["specjalizacja"] = $row["specjalizacja"];
                $response["success"] = 1;
 
                
                array_push($response["lekarz"], $lekarz);
            }
         

            echo json_encode($response);
        } else {
            $response["success"] = 2;
			$response["message"] = "Empty result";	

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