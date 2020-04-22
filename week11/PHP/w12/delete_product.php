<?php

// array for JSON response
$response = array();

$koneksi = mysqli_connect("localhost", "root", "", "android_w12");

// check for POST data
if(isset($_POST['pid'])){
	$pid = $_POST['pid'];
	$sql = "DELETE FROM products WHERE id = '$pid'";
	$result = mysqli_query($koneksi, $sql);

	// check if row deleted or not
	if(mysqli_affected_rows($koneksi) > 0){
		// success
		$response['success'] = 1;
		$response['message'] = "Product successfully deleted.";
		// echoing JSON response
		echo json_encode($response);
	}else{
		// no product found
		$response['success']= 0;
		$response['message'] = "No product found.";
		// echoing JSON response
		echo json_encode($response);
	}
}else{
	// required field is missing
	$response['success']= 0;
	$response['message'] = "Required field(s) is missing.";
	// echoing JSON response
	echo json_encode($response);
}