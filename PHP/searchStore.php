<?php
  $con = mysqli_connect("localhost", "root","zx153153","USER");

  $userID = $_POST["userID"];
  $sensorName = $_POST["sensorName"];
  $sensorLocation = $_POST["sensorLocation"];
  $code = $_POST["code"];

    $sql = "SELECT * FROM Store";

    $response = array();
    $response["success"] = false;

    $result = mysqli_query($con, $sql);

    $i = 0;
    while($row = mysqli_fetch_array($result)){
      $response["success"] = true;
      $response["storeName".$i] = $row["storeName"];
      $i++;
    }
    $response["storeCount"] = $i;

  echo json_encode($response);
?>
