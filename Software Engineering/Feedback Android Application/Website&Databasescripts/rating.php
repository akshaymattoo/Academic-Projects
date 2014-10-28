<?php
$user_id=$_POST["userid"];
$rtMusic = floatval($_POST["musicRating"]);
$rtService = floatval($_POST["serviceRating"]);
$rtFood= floatval($_POST["foodRating"]);
$rtAmbience=floatval( $_POST["ambienceRating"]);
$comments= $_POST["comments"];

$con=mysqli_connect("omega.uta.edu","axm5553","Rb31kU83","axm5553");
//$con=mysqli_connect("localhost","root","root","Feedback");
// Check connection
if (mysqli_connect_errno())
  {
  	echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
$queryMusic = "INSERT into user_rating(user_rating_id,user_id,rating_id,actual_rating) values ('".uniqid()."','".$user_id."',1,".$rtMusic.")";
$queryService = "INSERT into user_rating(user_rating_id,user_id,rating_id,actual_rating) values ('".uniqid()."','".$user_id."',2,".$rtService.")";
$queryFood = "INSERT into user_rating(user_rating_id,user_id,rating_id,actual_rating) values ('".uniqid()."','".$user_id."',3,".$rtFood.")";
$queryAmbience="INSERT into user_rating(user_rating_id,user_id,rating_id,actual_rating) values ('".uniqid()."','".$user_id."',4,".$rtAmbience.")";

$resultMusic = mysqli_query($con,$queryMusic,MYSQLI_USE_RESULT);
$resultService = mysqli_query($con,$queryService,MYSQLI_USE_RESULT);
$resultFood= mysqli_query($con,$queryFood,MYSQLI_USE_RESULT);
$resultAmbience = mysqli_query($con,$queryAmbience,MYSQLI_USE_RESULT);


$queryComments = "INSERT into comments(comment_id,user_id,comments) values('".uniqid()."','".$user_id."','".$comments."')";
$resultComments = mysqli_query($con,$queryComments,MYSQLI_USE_RESULT);


 if($resultMusic && $resultService && $resultFood && $resultAmbience){
      echo "Success";
    }
    else{
      echo "Failure";
    
    }

mysqli_close($con);
?>