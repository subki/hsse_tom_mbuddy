<?php
    include("koneksiDB.php");

   // array for JSON response
$response = array();
 
    $nik= $_POST['nik'];
    $kode= $_POST['kode'];
    $nilai= $_POST['nilai'];
    $tgl = $_POST['tgl'];
    $trainer= $_POST['trainer'];
 
 	$query = mysql_query("SELECT * from karyawan_training where nik='$nik' and kode='$kode'") 
 		or die("Unable to verify user because : " . mysql_error());
 		
 	if (mysql_num_rows($query) == 0){
        	$insert = mysql_query("INSERT into karyawan_training (nik, kode, nilai, tgl, trainer) values 
        		('$nik','$kode','$nilai','$tgl','$trainer')");
        		if($insert){
        			$response["success"] = 1;
        			$response["message"] = "successfully created.";
 
			        // echoing JSON response
			        echo json_encode($response);
    			} else {
        			// failed to insert row
			        $response["success"] = 0;
			        $response["message"] = "Oops! An error occurred.";
			 
			        // echoing JSON response
			        echo json_encode($response);
    			}
    	}else {
        	$update = mysql_query("Update karyawan_training set nilai='$nilai', trainer='$trainer', tgl='$tgl' 
        		WHERE nik = '$nik' and kode='$kode'");
        		//if($insert){
        			$response["success"] = 1;
        			$response["message"] = "successfully update.";
 
			        // echoing JSON response
			        echo json_encode($response);
    			//} else {
        			// failed to insert row
			//        $response["success"] = 0;
			//        $response["message"] = "Oops! An error occurred.";
			 
			        // echoing JSON response
			//        echo json_encode($response);
    			//}
    	}	

?>