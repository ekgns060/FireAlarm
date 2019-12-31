<?php
  $con = mysqli_connect("localhost", "root","zx153153","USER");

  $Token = $_POST["Token"];
  $userID = $_POST["userID"];
  $sql = "UPDATE USER SET Token=\"$Token\" WHERE userID = \"$userID\"";

  mysqli_query($con, $sql);
  mysqli_commit($con);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
