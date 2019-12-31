<?php
  $con = mysqli_connect("localhost", "root","zx153153","USER");

  $userPassword = $_POST["userPassword"];
  $userID = $_POST["userID"];
  $userName = $_POST["userName"];
  $userAge = $_POST["userAge"];
  $userAddress = $_POST["userAddress"];
  $userEmail = $_POST["userEmail"];
  $userPhone = $_POST["userPhone"];


  $response = array();
  $response["success"] = true;


  if($userID == null || $userPassword == null || $userName == null || $userAge == null || $userAge == "0" || $userAddress == null || $userEmail == null || $userPhone == null) {
    $response["success"] = false;
  }

  $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ?");
  mysqli_stmt_bind_param($statement, "s", $userID);
  mysqli_stmt_execute($statement);

  while(mysqli_stmt_fetch($statement)){
    $response["success"] = false;
  }

  $Token = null;
  $groupName = null;

  if($response["success"] == ture) {
    $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssisssss", $userID, $userPassword, $userName, $userAge, $userAddress, $userEmail, $userPhone, $groupName, $Token);
    mysqli_stmt_execute($statement);
  }

  echo json_encode($response);
?>
