<?php
  $con = mysqli_connect("localhost", "root","zx153153","USER");

  $sensorName = $_POST["sensorName"];
  //$sensorName = "888";
  //$userPassword = $_POST["userPassword"];
  //$userPassword = "qwert";

  $statement = mysqli_prepare($con, "SELECT * FROM Sensor WHERE sensorName = ?");
  mysqli_stmt_bind_param($statement, "s", $sensorName);
  mysqli_stmt_execute($statement);

  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $userID, $sensorName, $sensorLocation);

  $response = array();
  //$response["success"] = false;

  while(mysqli_stmt_fetch($statement))
  {
    //$response["success"] = true;
    //$response["userID"] = $userID;
    //$response["sensorLocation"] = $sensorLocation;
  }
  //echo json_encode($response);
  //echo $userID;

  $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ?");
  mysqli_stmt_bind_param($statement, "s", $userID);
  mysqli_stmt_execute($statement);

  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $userID, $userPassword, $userName, $userAge, $userAddress, $userEmail, $userPhone, $groupName, $Token);

  while(mysqli_stmt_fetch($statement))
  {
    //$response["groupName"] = $groupName;
  }

  $sql = "SELECT * FROM USER WHERE groupName = \"$groupName\"";
  $result = mysqli_query($con, $sql);

  $i = 0;
  while($row = mysqli_fetch_array($result)){
    $response[] = $row["Token"];
    $i++;
  }
  //$response["sensorCount"] = $i;

  echo json_encode($response);
  echo $sensorLocation;
  //echo $Token." ".$sensorLocation;
?>
