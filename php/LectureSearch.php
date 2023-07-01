<?php
	header('Content-Type: text/html; charset=utf-8');
	error_reporting(E_ALL);

    ini_set('display_errors', '1');
    $con = mysqli_connect("sever", "name", "pw", "DB");
	
    $statement = null;
    $lecture_no = null;
    $check = null;
    $ex = "교양";
    
    if(isset($_POST["check"]) || isset($_POST["student_id"])){
        $lecture_no = $_POST["lecture_no"];
        $student_id = $_POST["student_id"];
        $check = $_POST["check"];
    }


   if($check == '1'){
    	if($lecture_no != ""){
    		$statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Student ON Lecture.major = Student.major AND Lecture.grade <= Student.grade
			WHERE Lecture.lecture_no = ? AND Student.student_id = ?;
			");
			
        	mysqli_stmt_bind_param($statement, "ss", $lecture_no, $student_id);
    	}
    	
    	else{
    		$statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Student ON Lecture.major = Student.major AND Lecture.grade <= Student.grade
			WHERE Student.student_id = ?;
			");
        	mysqli_stmt_bind_param($statement, "s", $student_id);
    	}
        
        mysqli_stmt_execute($statement);
    }
    
    elseif($check == '2'){
    	
    	if($lecture_no != ""){
			$statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Student ON Lecture.major = Student.double_major AND Lecture.grade <= Student.grade
			WHERE Lecture.lecture_no = ? AND Student.student_id = ?;
			");
	        mysqli_stmt_bind_param($statement, "ss", $lecture_no, $student_id);
    	}
    	
    	else{
			$statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Student ON Lecture.major = Student.double_major AND Lecture.grade <= Student.grade
			WHERE Student.student_id = ?;
			");
	        mysqli_stmt_bind_param($statement, "s",$student_id);
    	}
        
        mysqli_stmt_execute($statement);
    }
    
    elseif($check == '3'){
    	
    	    	
    	if($lecture_no != ""){
			$statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Student ON Lecture.major = Student.minor AND Lecture.grade <= Student.grade
			WHERE Lecture.lecture_no = ? AND Student.student_id = ?;
			");
	        mysqli_stmt_bind_param($statement, "ss", $lecture_no, $student_id);
    	}
    	
    	else{
			$statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Student ON Lecture.major = Student.minor AND Lecture.grade <= Student.grade
			WHERE Student.student_id = ?;
			");
	        mysqli_stmt_bind_param($statement, "s",  $student_id);
    	}
        
        mysqli_stmt_execute($statement);
    }
    
        
    elseif($check == '4'){
    	if($lecture_no != ""){
	        $statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Student ON Lecture.grade <= Student.grade
			WHERE Student.student_id = ? AND Lecture.type = ? AND Lecture.lecture_no = ?;");
	        mysqli_stmt_bind_param($statement, "sss", $student_id,$ex, $lecture_no);
    	}
    	
    	else{
			$statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Student ON Lecture.grade <= Student.grade
			WHERE Student.student_id = ? AND Lecture.type = ?;");
	        mysqli_stmt_bind_param($statement, "ss", $student_id, $ex);
    	}

        mysqli_stmt_execute($statement);
    }
    
            
    elseif($check == '5'){
    	if($lecture_no != ""){
	        $statement = mysqli_prepare($con, "SELECT Lecture.* 
	        FROM Lecture
	        JOIN Shopping_basket ON Lecture.lecture_no = Shopping_basket.lecture_no
	        WHEREE Shopping_basket.lecture_no = ? AND Shopping_basket.student_id = ?" );
	        mysqli_stmt_bind_param($statement, "ss", $lecture_no, $student_id);
    	}
    	
    	else{
	        $statement = mysqli_prepare($con, "SELECT Lecture.*
			FROM Lecture
			JOIN Shopping_basket ON Lecture.lecture_no = Shopping_basket.lecture_no
			WHERE Shopping_basket.student_id = ?");
	        mysqli_stmt_bind_param($statement, "s",$student_id);
    	}
        
        mysqli_stmt_execute($statement);
    }
    
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $lecture_no, $lecture_title, 
    $professor, $major, $type, $time, $syllabus, 
    $headcount_max, $headcount, $headcount_basket, $grade, $credit);

    $result = array();
    $response = array();
 
    while(mysqli_stmt_fetch($statement)) {
        $response["lecture_no"] = $lecture_no;
        $response["lecture_title"] = $lecture_title;   
        $response["professor"] = $professor;
        $response["major"] = $major;
        $response["time"] = $time;
        $response["grade"] = $grade;
        $response["type"] = $type;    
        $response["headcount_basket"] = $headcount_basket;
        $result[] = $response;
        
    }

    echo json_encode($result, JSON_UNESCAPED_UNICODE);
?>