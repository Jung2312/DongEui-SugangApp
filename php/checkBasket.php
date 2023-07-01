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

    // 중복 신청 비교
    $basketstmt = mysqli_prepare($con, "SELECT *
    FROM Shopping_basket
    WHERE lecture_no = ? AND student_id = ?;");
        
    mysqli_stmt_bind_param($basketstmt, "ss", $lecture_no, $student_id);
    mysqli_stmt_execute($basketstmt);
        
    mysqli_stmt_store_result($basketstmt);
    mysqli_stmt_bind_result($basketstmt, $no, $checkBasketno, $checkBasketid);
    
    while(mysqli_stmt_fetch($basketstmt)) {
        $no = $no;
    }
    
    $errormsg["checkBasket"] = true;

    if($no != null){
        $errormsg["checkBasket"] = false;
    }
    
    echo json_encode($errormsg, JSON_UNESCAPED_UNICODE);

?>