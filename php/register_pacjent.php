
<?php

$response = array();
 

if (isset($_POST['imie']) && isset($_POST['nazwisko']) &&
    isset($_POST['pesel']) && isset($_POST['telefon']) && 
        isset($_POST['email']) && isset($_POST['adres']) && 
            isset($_POST['gender']) && isset($_POST['userId'])){
 
    $imie = $_POST['imie'];
    $nazwisko = $_POST['nazwisko'];
    $pesel = $_POST['pesel'];
    $telefon = $_POST['telefon'];
    $email = $_POST['email'];
    $adres = $_POST['adres'];
    $gender = $_POST['gender'];
    $userId = $_POST['userId'];
    


    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO med.pacjent(imie, nazwisko, pesel, telefon, email, adres, plec, userId) VALUES('$imie','$nazwisko','$pesel','$telefon','$email','$adres','$gender','$userId')");
 
    
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