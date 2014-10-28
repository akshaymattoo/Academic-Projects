<?php
$firstName = $_POST["firstname"];
$lastName = $_POST["lastname"];
$userName = $_POST["username"];
$password = $_POST["password"];
$number = $_POST["number"];
$email = $_POST["email"];


$con=mysqli_connect("omega.uta.edu","axm5553","Rb31kU83","axm5553");
// Check connection
if (mysqli_connect_errno())
  {
  	echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
$query = "INSERT into admin(first_name,last_name,user_name,password,phone_number,email) values ('".$firstName."','".$lastName."','".$userName."','".$password."',".$number.",'".$email."')";


$result = mysqli_query($con,$query,MYSQLI_USE_RESULT);



 if($result){
      echo "Success";
    }
    else{
      echo "Failure";
    
    }

mysqli_close($con);
?>