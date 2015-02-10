
<?php

$response = array();

if (isset($_POST['badanieId']) && isset($_POST['userId'])){
 
    $badanieId = $_POST['badanieId'];
    $userId = $_POST['userId'];
    
 
    
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
    $check = mysql_query("SELECT userId FROM med.badanie WHERE med.badanie.badanieId = $badanieId");

    if(!empty($check)){

          if($check['userId'] === NULL){
            $result = mysql_query("UPDATE med.badanie set med.badanie.userId=$userId where med.badanie.badanieId = $badanieId");
            if ($result) {
    
             $response["success"] = 1;
             $response["message"] = "Examination successfully Booked. $userId";
 
             echo json_encode($response);
           }
          } else {
  
             $response["success"] = 2;
              $response["message"] = "Examination is Taken.";
 

                echo json_encode($response);
          }
    }
    
} else {

        $response["success"] = 0;
        $response["message"] = "Required field(s) is missing";
 
     echo json_encode($response);
}



    
?>