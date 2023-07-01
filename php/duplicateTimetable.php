<?php
	error_reporting(E_ALL);

    ini_set('display_errors', '1');
    $con = mysqli_connect("sever", "name", "pw", "DB");
    mysqli_query($con,'SET NAMES utf8');

    
    
    if(isset($_POST["student_id"]) || isset($_POST["lecture_no"])){
	    $student_id = $_POST["student_id"];
	    $lecture_no = $_POST["lecture_no"];
    }
    
	$errormsg = array();
	
	$mon = null;
	$tue = null;
	$wed = null;
	$thu = null;
	$fri = null;	
	
	$mon2 = null;
	$tue2 = null;
	$wed2 = null;
	$thu2 = null;
	$fri2 = null;
	
	$monintens = null;
    $tueintens = null;
	$wedintens = null;
    $thuintens = null;
    $friintens = null;
   
	$Monday = null;
    $Tuesday = null;
	$Wednesday = null;
    $Thursday = null;
    $Friday = null;
    
   
    
    //간 비교
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
    	
    	$words = explode('/' , $time);
   		$i = 0;
   		while($i < count($words))
   		{
   			$word = mb_substr($words[$i], 0, 1);
			if($word == '월')
	        {
	       		$mon = explode(':' , $words[$i]);
	        }
	       
	        if($word == "화")
	        {
	       		$tue = explode(':' , $words[$i]);
	        }
	       
	        if($word == "수")
	        {
	       		$wed = explode(':' , $words[$i]);
	        }
	       
	        if($word == "목")
	        {
	       		$thu = explode(':' , $words[$i]);
	        }
	       
	        if($word == "금")
	        {
	       		$fri = explode(':' , $words[$i]);
	        }
	        $i = $i + 1;
   		}
        
       
    }
    
    if($mon != ""){
    	
    	$Monday = explode(',' , str_replace(array("\\","{","}", "[", "]", "\"") ,"", json_encode(array_filter($mon))));
    }
    
    if($tue != ""){
    	$Tuesday = explode(',' ,  str_replace(array("\\","{","}", "[", "]", "\"") ,"", json_encode(array_filter($tue))));
	}
    
    if($wed != ""){
    	$Wednesday = explode(',' , str_replace(array("\\","{","}", "[", "]", "\"") ,"", json_encode(array_filter($wed))));
    }
    
    if($thu != ""){
    	$Thursday = explode(',' , str_replace(array("\\","{","}", "[", "]", "\"") ,"", json_encode(array_filter($thu))));
    }
    
    if($fri != ""){
    	$Friday = explode(',' , str_replace(array("\\","{","}", "[", "]", "\"") ,"", json_encode(array_filter($fri))));
    }
    
    
    $timestmt = mysqli_prepare($con, "SELECT Lecture.*
    FROM Lecture
    JOIN Registration ON Registration.lecture_no = Lecture.lecture_no
    WHERE Registration.student_id = ?;
    ");
        
    mysqli_stmt_bind_param($timestmt, "s", $student_id);
    mysqli_stmt_execute($timestmt);
        
    mysqli_stmt_store_result($timestmt);
    mysqli_stmt_bind_result($timestmt, $lecture_no, $lecture_title, 
    $professor, $major, $type, $time, $syllabus, 
    $headcount_max, $headcount, $headcount_basket, $grade, $credit);
    
    $Mon = array();
    $Tue = array();
    $Wed = array();
    $Thu = array();
    $Fri = array();

    
    while(mysqli_stmt_fetch($timestmt)) {	
        $words = explode('/' , $time);
   		$i = 0;
   		while($i < count($words)){
   			$word = mb_substr($words[$i], 0, 1);
			if($word == "월")
	        {
	       		$mon2[] = explode(':' , $words[$i]);
	       		
	        }
	       
	        if($word == "화")
	        {
	       		$tue2[] = explode(':' , $words[$i]);
	        }
	       
	        if($word == "수")
	        {
	       		$wed2[] = explode(':' , $words[$i]);
	        }
	       
	        if($word == "목")
	        {
	       		$thu2[] = explode(':' , $words[$i]);
	        }
	       
	        if($word == "금")
	        {
	       		$fri2[] = explode(':' , $words[$i]);
	        }
	        $i = $i + 1;
   		}
   		
   		$Mon[] = $mon2;
   		$Tue[] = $tue2;
   		$Wed[] = $wed2;
   		$Thu[] = $thu2;
   		$Fri[] = $fri2;
    }
    

    
	$Mon = explode(',' , str_replace(array("uc6d4","\\","{","}", "[", "]", "\"") ,"", json_encode(array_filter($Mon))));
	
    $Tue = explode(',' , str_replace(array("ud654","\\","{","}", "[", "]", "\"") ,"",json_encode(array_filter($Tue))));
    
    $Wed = explode(',' , str_replace(array("uc218","\\","{","}","[", "]", "\"") ,"", json_encode(array_filter($Wed))));
    
    $Thu = explode(',' , str_replace(array("ubaa9","\\","{","}", "[", "]", "\"") ,"",json_encode(array_filter($Thu))));
    
    $Fri = explode(',' , str_replace(array("uae08","\\","{","}", "[", "]", "\"") ,"",json_encode(array_filter($Fri))));
    

    
    if($Monday != null){
		$monintens = array_intersect($Monday, $Mon);
    }
   
    if($Tuesday != null){
		$tueintens = array_intersect($Tuesday, $Tue);
	}
    
    if($Wednesday != null){
		$wedintens = array_intersect($Wednesday,$Wed);
    }
    
    if($Thursday != null){
		$thuintens = array_intersect($Thursday, $Thu);
    }
    
    if($Friday != null){
		$friintens = array_intersect($Friday, $Fri);
    }
    
	
	if(!empty($monintens) || !empty($tueintens) || !empty($wedintens) || 
	!empty($thuintens) ||  !empty($friintens) ||
	$monintens != null || $tueintens != null || $wedintens != null ||
	$thuintens != null || $friintens != null){
		$errormsg["timetable"] = false;
	}
	
	else{
		$errormsg["timetable"] = true;
	}
	
	echo json_encode($errormsg);
?>