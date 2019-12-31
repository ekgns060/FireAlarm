
<?php
header("Content-Type: text/html; charset=UTF-8");
?>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    new daum.Postcode({
        oncomplete: function(data) {
          if(data.userSelectedType=="R"){

               window.Android.setAddress(data.zonecode, data.roadAddress, data.buildingName);
           }
           else{
               window.Android.setAddress(data.zonecode, data.jibunAddress, data.buildingName);
           }
        }
    }).open();
</script>
