<?php
	error_reporting(E_ALL);

    ini_set('display_errors', '1');
    $con = mysqli_connect("sever", "name", "pw", "DB");
    mysqli_query($con,'SET NAMES utf8');
    
    $errormsg = array();

    if( isset($_POST["lecture_no"])){
      
        $lecture_no = $_POST["lecture_no"];
    }

    //인원 초과 비교
    $statement = mysqli_prepare($con, "SELECT Lecture.*
    FROM Lecture
    WHERE Lecture.lecture_no = ?;
    ");
        
    mysqli_stmt_bind_param($statement, "s",$lecture_no);
    mysqli_stmt_execute($statement);
        
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $lecture_no, $lecture_title, 
    $professor, $major, $type, $time, $syllabus, 
    $headcount_max, $headcount, $headcount_basket, $grade, $credit);

    
    while(mysqli_stmt_fetch($statement)) {
        $headcount_max = $headcount_max;
        $headcount = $headcount;
    }
    
    $errormsg["headcount"] = true;

    if($headcount_max <= $headcount){
        $errormsg["headcount"] = false;
    }
    
    echo json_encode($errormsg, JSON_UNESCAPED_UNICODE);
?>