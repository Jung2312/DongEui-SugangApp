<?php
	error_reporting(E_ALL);

    ini_set('display_errors', '1');
    $con = mysqli_connect("sever", "name", "pw", "DB");
    mysqli_query($con,'SET NAMES utf8');

    
	if(isset($_POST["student_id"]) && isset($_POST["password"])){
        $student_id = $_POST["student_id"];
        $password = $_POST["password"];
    }
    
	$statement = mysqli_prepare($con, "SELECT * FROM Student WHERE student_id = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $student_id, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);   
    mysqli_stmt_bind_result($statement, $student_id, $password, $name, $major, $double_major, $minor, $grade);
    
    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
       $response["success"] = true;
       $response["student_id"] = $student_id;
       $response["password"] = $password;
       $response["name"] = $name;
       $response["major"] = $major;
       $response["double_major"] = $double_major;
       $response["minor"] = $minor;
       $response["grade"] = $grade;     
    }

    echo json_encode($response);

    
?>