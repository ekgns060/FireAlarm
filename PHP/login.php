<?php
  $con = mysqli_connect("localhost", "root","zx153153","USER");

  $userID = $_POST["userID"];
  //$userID = "zx";
  $userPassword = $_POST["userPassword"];
  //$userPassword = "zx";
  $sql = "SELECT * FROM USER WHERE userID = \"$userID\" AND userPassword = \"$userPassword\"";

  $response = array();
  $response["success"] = false;

  $result = mysqli_query($con, $sql);

  while($row = mysqli_fetch_array($result)){
    $response["success"] = true;
    $response["userID"] = $row["userID"];
    $response["userPassword"] = $row["userPassword"];
    $response["userName"] = $row["userName"];
    $response["userAge"] = $row["userAge"];
    $response["userAddress"] = $row["userAddress"];
    $response["userEmail"] = $row["userEmail"];
    $response["userPhone"] = $row["userPhone"];
  }
  //echo json_encode($response);

  $sql = "SELECT * FROM Sensor WHERE userID = \"$userID\"";
  $result = mysqli_query($con, $sql);

  $i = 0;
  while($row = mysqli_fetch_array($result)){
    $response["sensorName".$i] = "센서: ".$row["sensorName"]."   /   위치: ".$row["sensorLocation"];
    $i++;
  }
  $response["sensorCount"] = $i;

  echo json_encode($response);
?>
