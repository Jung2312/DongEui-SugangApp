<?php
	error_reporting(E_ALL);

    ini_set('display_errors', '1');
    $con = mysqli_connect("sever", "name", "pw", "DB");
    mysqli_query($con,'SET NAMES utf8');
    

    
	if(isset($_POST["admin_id"]) || isset($_POST["password"])){
        $admin_id = $_POST["admin_id"];
        $password = $_POST["password"];
    }
    
	$statement = mysqli_prepare($con, "SELECT * FROM Admin WHERE admin_id = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $admin_id, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);   
    mysqli_stmt_bind_result($statement, $admin_id, $password);
    
    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
       $response["success"] = true;
       $response["admin_id_id"] = $admin_id;
       $response["password"] = $password;   
    }

    echo json_encode($response);

    
?>