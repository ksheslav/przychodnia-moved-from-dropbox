<?php

$response = array();
 
require_once __DIR__ . '/db_connect.php';
 
$db = new DB_CONNECT();
 

if (isset($_POST['getexaminationlist'])) {
    $lekarzId = $_POST['getexaminationlist'];
    $result = mysql_query("SELECT badanieId ,userId, dataBadania, nazwabadania, sala, accepted FROM med.badanie as b where b.lekarzId = $lekarzId");
 
    if (!empty($result)) {
		
        if (mysql_num_rows($result) > 0) {

            $response["badanie"] = array();

              while ($row = mysql_fetch_array($result)){
              
               $badanie = array();
               $badanie["badanieId"] = $row["badanieId"];
                $badanie["dataBadania"] = $row["dataBadania"];
                $badanie["nazwabadania"] = $row["nazwabadania"];
                $badanie["userId"] = $row["userId"];
                $badanie["sala"] = $row["sala"];
                $badanie["accepted"] = $row["accepted"];
                $response["success"] = 1;
 
                
                array_push($response["badanie"], $badanie);
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