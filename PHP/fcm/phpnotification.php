<?php

	function send_notification ($token, $message)
	{
    $apikey = "AAAA0MMrBAI:APA91bGJBTiUKHbaigV9rXUFF5aYbswpj2VcZ3rXQdDf2jGvd-AbJlEwbHegXVN7PMqHMvL6evH5jlGtsPf_YVWskX8MGj278I3mN9HLUGyzEmk0MFncrca_HGIsONKN02qIiCX3dFR6";
		//$apikey = "AIzaSyC7k0rFvmW1SbJLZCj-0n5edV5-ZhN4JZM";
		$url = "https://fcm.googleapis.com/fcm/send";
		$fields = array( 'to' => $token, 'data' => $message);

		$headers = array( 'Authorization:key ='.$apikey, 'Content-Type: application/json');

	  $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
    $result = curl_exec($ch);
    if ($result === FALSE) {
          die('Curl failed: ' . curl_error($ch));
      }
      curl_close($ch);
      return $result;
	}

	//$token = "eFpQ4heX3oY:APA91bE0N7utOdopdQBJG1E74mW8f90c6dIMgNmpMxf5wfgkj15zB-hfpG7Tov6ohj8zmS_SsyOoBa6gRbnZve5GDX9BygtPDw5B-SmPGNr4krCj00CJq7tTgrc5BBf0Woq4KPjzIj0a";
	//$token = "c7WR7-abtvQ:APA91bFWLp4VQE1iRrmL1H2tZm4gp1Rnsva9ZrgeCG4JHMUjE8CmoZ63wSBUqPoStPv7lmxDXFuOdAKpMhMRyeOE4Glg6ixTFN1pEa8vmtrnfEqt698XVFJKav8rYIr0TUfq5liF-gnc";
	//$token = "cMr16hcDw-Q:APA91bE8KYSbPv1r-4MBM8EoF2UR60ph8SN6vT8uZn0qF1ybjhofvkEMrXpfxMIyGhlX9OHqT_MF_bwjVt83pwuzd7JXzsNEry3T6K-d6OHBGSSSTCGedrx6VGuJZodLHhw93d0XfKmB";
	//$token = "c7WR7-abtvQ:APA91bFWLp4VQE1iRrmL1H2tZm4gp1Rnsva9ZrgeCG4JHMUjE8CmoZ63wSBUqPoStPv7lmxDXFuOdAKpMhMRyeOE4Glg6ixTFN1pEa8vmtrnfEqt698XVFJKav8rYIr0TUfq5liF-gnc";
	//서버 토큰
	//$token = $_POST["token"];
  $token = $_GET["token"];
	$token2 = $_GET["token2"];
  //$token2 = "e1IIZc7PuYI:APA91bGQUc3vVuA5zjdbxRZmLABIZRD_mJvglaD--g5rIapMoOSroQFm-lPjAxOllgALyRruACMCIP00BGb71daHcW1Sfq8lAEwYSYleX8HYyp7C7z30R0wDwvlF_pu5_gO7W0hl2qV9";
	//$token2 = "eqbQcuIjexY:APA91bEn_vS4r92e1h0U1u7KRZ4C2AMIQhtxsvmDZ-lceuALZ--yqkw-X-vzBHK-R9cNqatRNgu5tMSfgTQg6HhrqRNz_IEEIMSD2SlWfwceK8JoHL3x6e5MaECYMfpabAvel116rWlr";
	//이현욱 토큰
	//$token = "cV2VJYGVRX0:APA91bGRMNash3s-mMRNW7Ez4wDoY57rQY8msbS8lfwODVVj1uBEkiu4QzuNXOsV_OJXpcg8kUKKqBc8BieuT8A6k2Yj8g_rhK8qJgdNW3ydXVABggrTjEegauFO2JrvUd_0quwJSzfX";
	//이현욱 권효진 토큰
	//$token = "cNxuT_tAEEc:APA91bHbM4FS7HZIZ8inMOUfHY0M5B61v-vSUpU0x5tNboe_JwamX6P6gETSMhzdNnyAPG_pxqbjSo7g5yuTJI2vCid3w2SwLmVdFhCH5VHIk-FQOk3XDWpsUcNKcCHCW-h1Lc4s-UtP";
	//$token = "dInn5bcisPs:APA91bF0a6tJAlcKJEeTty8SqfgY6VH9L4iIDwK3xbmIMnqKpy9UgVGdQyPEWIYdOf4AQHtWKt_KZVdY8Tl5OEKXEvn0Mj4UxCMw2tNbqSuxvx8vq7tmpw_bDww1F7PfTwujb_LpYQ92";
	//$sensorLocation = $_GET["sensorLocation"];
	$sensorLocation =$_GET["sensorLocation"];
	$time = $_GET["time"];
	if($sensorLocation != "null") {
		$message = array(
			'title' => $time,
    	'body' => $sensorLocation.'에서 화재가 발생했습니다.',
			'sound' => 'default'

  	);
	} else {
		$message = array(
			'title' => $time,
    	'body' => '위치정보가 등록되지 않은 곳에서 화재가 발생했습니다.',
			'sound' => 'default',

  	);
	}



  echo send_notification($token, $message);
	echo send_notification($token2, $message);

?>
