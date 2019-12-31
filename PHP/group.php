<?php
  $con = mysqli_connect("localhost", "root","zx153153","USER");

  $groupName = $_POST["groupName"];
  $groupPassword = $_POST["groupPassword"];
  $userID = $_POST["userID"];
  $code = $_POST["code"];

  if($code == "1") {
    $sql = "SELECT * FROM Grouplist WHERE groupName LIKE \"$groupName\"";

    $response = array();
    $response["success"] = false;

    $result = mysqli_query($con, $sql);

    $i = 0;
    while($row = mysqli_fetch_array($result)){
      $response["success"] = true;
      $response["groupName".$i] = $row["groupName"];
      $i++;
    }
    $response["groupCount"] = $i;

  }elseif($code == "2") {

    $sql = "SELECT * FROM Grouplist WHERE groupName = \"$groupName\" AND groupPassword = \"$groupPassword\"";

    $response = array();
    $response["success"] = false;

    $result = mysqli_query($con, $sql);

    while($row = mysqli_fetch_array($result)){
      $response["success"] = true;
      $response["groupName"] = $row["groupName"];
      $response["groupPassword"] = $row["groupPassword"];
    }

    $sql = "SELECT * FROM Groupuser WHERE groupName = \"$groupName\"";
    $result = mysqli_query($con, $sql);

    $i = 0;
    while($row = mysqli_fetch_array($result)){
      $response["userID".$i] = "userID: ".$row["userID"];
      $response["groupName"] = $row["groupName"];
      $response["token"] = $row["token"];
      $i++;
    }
    $response["groupCount"] = $i;

  }elseif($code == "3") {

    $sql = "SELECT * FROM Groupuser WHERE userID = \"$userID\" AND groupName = \"$groupName\"";

    $response = array();
    $response["success"] = true;

    $result = mysqli_query($con, $sql);

    while($row = mysqli_fetch_array($result)){
      $response["success"] = false;
    }

    if($response["success"] == ture) {
      $statement = mysqli_prepare($con, "INSERT INTO Groupuser VALUES (?, ?)");
      mysqli_stmt_bind_param($statement, "ss", $userID, $groupName);
      mysqli_stmt_execute($statement);
    }

  }elseif($code == "4") {
    $sql = "SELECT * FROM Groupcancle WHERE userID = \"$userID\" AND groupName = \"$groupName\"";

    $response = array();
    $response["success"] = true;

    $result = mysqli_query($con, $sql);

    while($row = mysqli_fetch_array($result)){
      $response["success"] = false;
    }

    if($response["success"] == ture) {
      $statement = mysqli_prepare($con, "INSERT INTO Groupcancle VALUES (?, ?)");
      mysqli_stmt_bind_param($statement, "ss", $userID, $groupName);
      mysqli_stmt_execute($statement);
    }
  }

  echo json_encode($response);
?>
