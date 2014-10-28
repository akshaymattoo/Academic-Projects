<?php
$userName = $_POST["username"];
$password = $_POST["password"];

$con=mysqli_connect("omega.uta.edu","axm5553","Rb31kU83","axm5553");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$query = "SELECT first_name,last_name,user_name,phone_number,email FROM admin where user_name='".$userName."' and password = '".$password."'";


$result = mysqli_query($con,$query);
while($row = mysqli_fetch_array($result))
  {
  if(count($row) > 1)
    echo $row['first_name'].";".$row['last_name'].";".$row['user_name'].";".$row['phone_number'].";".$row['email'];
  else
    echo "Failure";
  }

mysqli_close($con); 
?>