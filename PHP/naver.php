<?php
  $con = mysqli_connect("localhost", "root","zx153153","USER");


  $userID = $_POST["userID"];
  $userName = $_POST["userName"];
  $userEmail = $_POST["userEmail"];

  $response = array();
  $response["success"] = true;

/*
  if($userID == null || $userName == null || $userEmail == null) {
    $response["success"] = false;
  }
*/

  $statement = mysqli_prepare($con, "SELECT * FROM Naver WHERE userID = ?");
  mysqli_stmt_bind_param($statement, "s", $userID);
  mysqli_stmt_execute($statement);


  while(mysqli_stmt_fetch($statement)){
    $response["success"] = false;
  }
  if($response["success"] == ture) {
    $statement = mysqli_prepare($con, "INSERT INTO Naver VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssissss", $userID, $userName, $userEmail, $Token);
    mysqli_stmt_execute($statement);
  }


  echo json_encode($response);
?>
