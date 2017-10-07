<%@ tag body-content="empty"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"/> -->
<script src='http://code.jquery.com/jquery-latest.min.js' type='text/javascript'></script>
<script src="${resourcePath}/js/less-1.5.0.min.js" type="text/javascript"></script>

<script type="text/javascript">

	// Document READY!!!
	$(document).ready(function() {
		$.ajaxSetup({ cache: false });
		
		$('.systemSelector').on('change', function(e) {
			if (this.selectedIndex > 0){
				var selectedCatalogSystem = this.value;
				// 			e.preventDefault();
				changeCatalogSystem(selectedCatalogSystem);
				$('.tableSelector').clear();
			}
		});
		
		$('.migrateButton').on('click', function(e) {
			var packageToMigrate = this.value;
			migratePackage(packageToMigrate);
		});
	});

	// Change Catalog System
	function changeCatalogSystem(catalogSystem) {
		jQuery.getJSON(
				'http://localhost:18080/migration/explore/legacy/tableList?catalogSystem='
						+ catalogSystem, function(thisResponse) {

							 var $model = $('select.tableSelector');
					        $model.empty().append(function() {
					            var output = '';
					            $.each(thisResponse, function(key, value) {
					                output += '<option value=\"\">' + value + '</option>';
					            });
					            return output;
					        });
					        
				}).done(function() {
			//
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("error " + textStatus);
			alert("incoming Text " + jqXHR.responseText);
		}).always(function() {
			//
		});
	}
	
	function migratePackage(packageToMigrate) {
		jQuery.getJSON(
				'http://localhost:18080/migration/migrate/execute?packageName='
						+ packageToMigrate, function(thisResponse) {
					alert(thisResponse);
					$("#migrationResult").innerHTML = thisResponse;
				}).done(function() {
			//
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("error " + textStatus);
			alert("incoming Text " + jqXHR.responseText);
		}).always(function() {
			//
		});
	}
</script>
