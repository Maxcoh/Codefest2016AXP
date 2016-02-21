
<?php
	$url = "$_SERVER[REQUEST_URI]";
	$argOffset = strrpos($url,"tableName=")+10;
	$argLen =  strlen($url) - $argOffset;

	$tableName = substr($url,$argOffset,$argLen);
	$tableName = "`".str_replace("%20"," ",$tableName)."`";
	
	$serverName = "localhost";
	$userName = "test";
	$userPassword="test";
	$dataBaseName = "mysql";
	
	$connection = new mysqli($serverName,$userName,$userPassword,$dataBaseName);
	
	if($connection -> connect_error){
		die("Connection failed: " . $connection->connect_error);
	}
	
	$sql = "SELECT * FROM " . $tableName;
	$response = $connection->query($sql);
	
	if($response->num_rows > 0)
		while($row = $response->fetch_assoc()){
			echo $row["OID"] . ", " . $row["SYSTEM"] . ", " . $row["MONTHDAY_INSTALL"] . ", " . $row["YEAR_INSTALLED"] . ", " . $row["LIFECYCLESTATUS"] . ", " . $row["OWNER"] . ", " . $row["SUBTYPE"] . ", " . $row["HYDRAULICID"] . ", " . $row["FACILITYID"] . ", " . $row["VEGETATION"] . ", " . $row["SHAPE_AREA"] . ", " . $row["SHAPE_LEN"] . '\n';
		}
		
	$connection->close();
?>