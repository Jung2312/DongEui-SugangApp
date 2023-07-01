<?php
	error_reporting(E_ALL);

    ini_set('display_errors', '1');
    $con = mysqli_connect("sever", "name", "pw", "DB");
    mysqli_query($con,'SET NAMES utf8');
    
    $errormsg = array();

    if(isset($_POST["student_id"]) || isset($_POST["lecture_no"])){
        $student_id = $_POST["student_id"];
        $lecture_no = $_POST["lecture_no"];
    }

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
        $credit = $credit;
    }

    // 신청 학점 초과 비교
    $creditstmt = mysqli_prepare($con, "SELECT SUM(Lecture.credit) AS sumcredit
    FROM Lecture
    JOIN Registration ON Lecture.lecture_no = Registration.lecture_no
    WHERE Registration.student_id = ?;");
        
    mysqli_stmt_bind_param($creditstmt, "s", $student_id);
    mysqli_stmt_execute($creditstmt);
        
    mysqli_stmt_store_result($creditstmt);
    mysqli_stmt_bind_result($creditstmt, $sumcredit);

    while(mysqli_stmt_fetch($creditstmt)) {
        $sumcredit = $sumcredit;
    }
    
    $errormsg["credit"] = true;

    if($sumcredit >= 21 || ($sumcredit + $credit) > 21){
        $errormsg["credit"] = false;
    }

    echo json_encode($errormsg, JSON_UNESCAPED_UNICODE);

?>