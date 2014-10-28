<?php
$number = $_POST["number"];
$email = $_POST["email"];
$comments = $_POST["comments"];
$userName = $_POST["username"];
$password = $_POST["password"];


$con=mysqli_connect("omega.uta.edu","axm5553","Rb31kU83","axm5553");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
$query = "UPDATE admin set phone_number =".$number.",email='".$email."',comments='".$comments."' where user_name='".$userName."' and password = '".$password."'";


$result = mysqli_query($con,$query,MYSQLI_USE_RESULT);



 if($result){
      echo "Success";
    }
    else{
      echo "Failure";
    
    }

mysqli_close($con);
?>