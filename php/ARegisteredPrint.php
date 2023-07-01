<?php
	error_reporting(E_ALL);

    ini_set('display_errors', '1');
    $con = mysqli_connect("sever", "name", "pw", "DB");
    mysqli_query($con,'SET NAMES utf8');

    if(isset($_POST["student_id"])){
        $student_id = $_POST["student_id"];
    }
    
    $statement = mysqli_prepare($con, "SELECT Lecture.*
    FROM Lecture
    JOIN Registration ON Lecture.lecture_no = Registration.lecture_no
    JOIN Student ON Registration.student_id = Student.student_id
    WHERE Student.student_id = ?;
    ");
    
    mysqli_stmt_bind_param($statement, "s", $student_id);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $lecture_no, $lecture_title, 
    $professor, $major, $type, $time, $syllabus , 
    $headcount_max, $headcount, $headcount_basket, $grade, $credit);

    $result = array();
    $response = array();
 
    while(mysqli_stmt_fetch($statement)) {
        $response["lecture_no"] = $lecture_no;
        $response["lecture_title"] = $lecture_title;   
        $response["professor"] = $professor;
        $response["time"] = $time;      
        $response["type"] = $type;      
        $result[] = $response;
    }

    echo json_encode($result, JSON_UNESCAPED_UNICODE);
?>