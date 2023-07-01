<?php
	error_reporting(E_ALL);

    ini_set('display_errors', '1');
    $con = mysqli_connect("sever", "name", "pw", "DB");
    mysqli_query($con,'SET NAMES utf8');
    
    $check = null;

    if(isset($_POST["student_id"]) || isset($_POST["lecture_no"]) || isset($_POST["check"])){
        $student_id = $_POST["student_id"];
        $lecture_no = $_POST["lecture_no"];
        $check = $_POST["check"];
    }
    
    if($check == "Del"){
	    $statement = mysqli_prepare($con, "DELETE FROM Shopping_basket 
	    WHERE Shopping_basket.lecture_no = ? AND Shopping_basket.student_id = ?");
	
	    mysqli_stmt_bind_param($statement, "ss",$lecture_no, $student_id);
	    mysqli_stmt_execute($statement);  
	    
	    $statement = mysqli_prepare($con, "UPDATE Lecture
		SET headcount_basket = headcount_basket - 1
		WHERE lecture_no = ?;");
	
	    mysqli_stmt_bind_param($statement, "s",$lecture_no);
	    mysqli_stmt_execute($statement);
	 }
	 
	 elseif($check == "Add"){
	 	$statement = mysqli_prepare($con, "INSERT INTO Shopping_basket (lecture_no, student_id)
		SELECT ?, ?
		WHERE NOT EXISTS (
			SELECT *
			FROM Shopping_basket
			WHERE lecture_no = ? AND student_id = ?);");
	
	    mysqli_stmt_bind_param($statement, "ssss",$lecture_no, $student_id, $lecture_no, $student_id);
	    mysqli_stmt_execute($statement);
	    mysqli_stmt_close($statement);
	    
	    $statement = mysqli_prepare($con, "UPDATE Lecture
		SET headcount_basket = headcount_basket + 1
		WHERE lecture_no = ?;");
	
	    mysqli_stmt_bind_param($statement, "s",$lecture_no);
	    mysqli_stmt_execute($statement);
	 }
	 

	 
?>