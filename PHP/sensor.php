<?php
  $con = mysqli_connect("localhost", "root","zx153153","USER");

  $userID = $_POST["userID"];
  $sensorName = $_POST["sensorName"];
  $sensorLocation = $_POST["sensorLocation"];
  $code = $_POST["code"];

if($code != "2") {
  if($code == "1") {
      $statement = mysqli_prepare($con, "SELECT * FROM Sensor WHERE sensorName = ?");
      mysqli_stmt_bind_param($statement, "s", $sensorName);
  } else {
    $statement = mysqli_prepare($con, "SELECT * FROM Sensor WHERE userID = ? AND sensorName = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $sensorName);
  }
  mysqli_stmt_execute($statement);

  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $userID, $sensorName);
  mysqli_stmt_execute($statement);
  $response = array();

  if($code == "1") {
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)){
      $response["success"] = false;
    }
  }
  else{
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
      $response["success"] = true;
    }
  }

  if($code == "1" && $response["success"] == ture) {///등록
    $statement = mysqli_prepare($con, "INSERT INTO Sensor VALUES (?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sss", $userID, $sensorName, $sensorLocation);
    mysqli_stmt_execute($statement);
  } elseif($code == "3") {
    $statement = mysqli_prepare($con, "UPDATE Sensor SET sensorLocation = ? WHERE userID = ? AND sensorName = ?");
    mysqli_stmt_bind_param($statement, "sss", $sensorLocation, $userID, $sensorName);
    mysqli_stmt_execute($statement);
  }elseif($code == "4") {
    $statement = mysqli_prepare($con, "DELETE FROM Sensor WHERE userID = ? AND sensorName = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $sensorName);
    mysqli_stmt_execute($statement);
  }
}


 elseif($code == "2") {
    $sql = "SELECT * FROM Sensor WHERE sensorLocation LIKE \"%$sensorLocation%\"";

    $response = array();
    $response["success"] = false;

    $result = mysqli_query($con, $sql);

    $i = 0;
    while($row = mysqli_fetch_array($result)){
      $response["success"] = true;
      $response["sensorLocation".$i] = $row["sensorLocation"];
      $i++;
    }
    $response["sensorCount"] = $i;
}


  echo json_encode($response);
?>
