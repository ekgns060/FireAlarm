<?php
    $url = "https://fcm.googleapis.com/fcm/send";
    $token = "cV2VJYGVRX0:APA91bGRMNash3s-mMRNW7Ez4wDoY57rQY8msbS8lfwODVVj1uBEkiu4QzuNXOsV_OJXpcg8kUKKqBc8BieuT8A6k2Yj8g_rhK8qJgdNW3ydXVABggrTjEegauFO2JrvUd_0quwJSzfX";
    $serverKey = "AIzaSyC7k0rFvmW1SbJLZCj-0n5edV5-ZhN4JZM";
    $title = "Notification title";
    $body = "Hello I am from Your php server";
    $notification = array('title' =>$title , 'body' => $body, 'sound' => 'default', 'badge' => '1');
    $arrayToSend = array('to' => $token, 'notification' => $notification,'priority'=>'high');
    $json = json_encode($arrayToSend);
    $headers = array();
    $headers[] = 'Content-Type: application/json';
    $headers[] = 'Authorization: key='. $serverKey;
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST,"POST");
    curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
    curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
    //Send the request
    $response = curl_exec($ch);
    //Close request
    if ($response === FALSE) {
    die('FCM Send Error: ' . curl_error($ch));
    }
    curl_close($ch);
?>
