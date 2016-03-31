<?php
    include("koneksiDB.php");

   // array for JSON response
$response = array();
 
    $nik= $_POST['nik'];
    $kode= $_POST['kode'];
 
 	$query = mysql_query("SELECT * from karyawan_training where nik='$nik' and kode='$kode'") 
 		or die("Unable to verify user because : " . mysql_error());
 		
 	if (mysql_num_rows($query) == 0){
        			$response["success"] = "blm";
        			$response["message"] = "successfully created.";
 
			        // echoing JSON response
			        echo json_encode($response);
    	} else {
        			// failed to insert row
			        $response["success"] = "ada";
			        $response["message"] = "Oops! An error occurred.";
			 
			        // echoing JSON response
			        echo json_encode($response);
    	}
    	
?>