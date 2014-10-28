<?php
$user_id= $_POST["userid"];
$name = $_POST["name"];
$number = $_POST["number"];
$email = $_POST["email"];
$date = $_POST["date"];
$today = date("Y-m-d");

$con=mysqli_connect("omega.uta.edu","axm5553","Rb31kU83","axm5553");
//$con=mysqli_connect("localhost","root","root","Feedback");
// Check connection
if (mysqli_connect_errno())
  {
  	echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
$query = "INSERT into user(user_id,name,phone_number,email,date_of_birth,current_dt) values ('".$user_id."','".$name."',".$number.",'".$email."','".$date."','".$today."')";


$result = mysqli_query($con,$query,MYSQLI_USE_RESULT);


 if($result){
      echo "Success";
    }
    else{
      echo "Failure";
    
    }

mysqli_close($con);
?>